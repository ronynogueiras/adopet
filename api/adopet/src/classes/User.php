<?php 

use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;
use \Interop\Container\ContainerInterface as ContainerInterface;

class User {

    private $ci;
    private $db;
    public function __construct(ContainerInterface $ci){
		$this->db = $ci->db;
		$this->logger = $ci->logger;
	}

    public function create(Request $request, Response $response, array $args){
        $params = $request->getParsedBody();

        if(!empty($params)){
            $mail = $params['mail'];
            $name = $params['name'];
            $password = $params['password'];
            $phone = $params['phone'];
            
            $query = $this->db->prepare("select id from user where mail=? and status='Active'");
            $query->execute( array($mail) );
            if($query->rowCount()==0){
                $password = password_hash($password.$mail, PASSWORD_BCRYPT, ['cost' => 12]);
                $query = $this->db->prepare("insert into user (name,mail,password,phone,register_at,status) values (?,?,?,?,now(),'Active')");
                $query->execute( array( $name, $mail, $password, $phone ) );

                if($query->rowCount()==1){
                    $message = array('message' => 'Cadastrado com sucesso');
                    $response = $response->withJson($message,200);
                }else{
                    $message = array('message' => 'Erro ao realizar o cadastro');
                    $response = $response->withJson($message,500);
                }
            }else{
                $message = array('message' => 'E-mail já cadastrado no sistema');
                $response = $response->withJson($message,409);
            }
        }else{
            $message = array('message' => 'Dados obrigatórios não informados');
            $response = $response->withJson($message,500);
        }
        return $response;
    }
    public function get(Request $request, Response $response, array $args){
        if(is_numeric($args['id'])){
            $id = $args['id'];
            $query = $this->db->prepare("select * from user where id=? and status='Active'");
            $query->execute(array( $id ));
            $user = $query->fetch(PDO::FETCH_ASSOC);
            if($query->rowCount()>0){
                $response = $response->withJson( array('user' => $user),200);
            }else{
                $error = array('message' => 'Usuário não encontrado');
                $response = $response->withJson($error,404);
            }            
        }else{
            $error = array('message' => 'Usuário não encontrado');
            $response = $response->withJson($error,500);
        }
        return $response;
    }
    public function update(Request $request, Response $response, array $args){
        $params = $request->getParsedBody();

        if(!empty($params)){
            $id = $params['id'];
            $mail = $params['mail'];
            $name = $params['name'];
            $password = $params['password'];
            $phone = $params['phone'];
            
            $query = $this->db->prepare("select id from user where id=? and status='Active'");
            $query->execute( array($id) );
            if($query->rowCount()==0){
                $password = password_hash($password.$mail, PASSWORD_BCRYPT, ['cost' => 12]);
                $query = $this->db->prepare("update user set name=?,mail=?,password=?,phone=? where id=?");
                $query->execute( array( $name, $mail, $password, $phone, $id ) );

                if($query->rowCount()==1){
                    $message = array('message' => 'Atualizado com sucesso');
                    $response = $response->withJson($message,200);
                }else{
                    $message = array('message' => 'Erro ao realizar a atualização');
                    $response = $response->withJson($message,500);
                }
            }else{
                $message = array('message' => 'Usuário não encontrado');
                $response = $response->withJson($message,404);
            }
        }else{
            $message = array('message' => 'Dados obrigatórios não informados');
            $response = $response->withJson($message,500);
        }
        return $response;
    }
    public function delete(Request $request, Response $response, array $args){
        
        if(is_numeric($args['id'])){
            $id = $args['id'];
            $query = $this->db->prepare("update user set status='Deleted' where id=?");
            $query->execute(array($id));
            if($query->rowCount()==1){
                $message = array('message' => 'Usuário removido com sucesso');
                $response = $response->withJson($message,200); 
            }else{
                $message = array('message' => 'Erro na remoção do usuário');
                $response = $response->withJson($message,401);
            }
        }else{
            $message = array('message' => 'Dados obrigatórios não informados');
            $response = $response->withJson($message,500);
        }
        
        return $response;
    }
    public function auth(Request $request, Response $response, array $args){
        
        $params = $request->getParsedBody();

        if(!empty($params)){
            $mail = $params['mail'];
            $password = $params['password'];

            $query = $this->db->prepare("select * from user where mail=? and status='Active'");
            $query->execute(array($mail));
            if($query->rowCount()>0){
                $user = $query->fetchObject();
                if(password_verify($password.$mail, $user->password)){
                    $message = array('message' => 'Autenticado com sucesso.');
                    $response = $response->withJson($message,200);
                }else{
                    $message = array('message' => 'Senha inválida');
                    $response = $response->withJson($message,400);
                }
            }else{
                $message = array('message' => 'E-mail inválido');
                $response = $response->withJson($message,400);
            }
        }else{
            $message = array('message' => 'Dados obrigatórios não informados');
            $response = $response->withJson($message,500);
        }
        
        return $response;
    }
    public function logout(Request $request, Response $response, array $args){
        
        $message = array('message' => 'Logout realizado com sucesso.');
        $response = $response->withJson($message,200);
        
        return $response;
    }
}
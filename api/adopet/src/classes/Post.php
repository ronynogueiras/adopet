<?php 

use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;
use \Interop\Container\ContainerInterface as ContainerInterface;

class Post {

    private $ci;
    private $db;

    public function __construct(ContainerInterface $ci){
		$this->db = $ci->db;
		$this->logger = $ci->logger;
	}

    public function create(Request $request, Response $response, array $args){
        
        $params = $request->getParsedBody();
        if(!empty($params)){
            $title = $params['title'];
            $description = $params['description'];
            $user_id = $params['uuid'];
            // $files = $request->getUploadedFiles();
            $picture = 'picture.png';
            
            $query = $this->db->prepare("insert into post (title, description, picture, user_id, date, status) values (?, ?, ?, ?, now(), 'Active')");
            $query->execute(array($title,$description,$picture,$user_id));
            
            if($query->rowCount()==1){
                $message = array('message' => 'Publicação realizada com sucesso');
                $response = $response->withJson($message,200);
            }else{
                $message = array('message' => 'Erro na publicação');
                $response = $response->withJson($message,500);
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
            $query = $this->db->prepare("select * from post where id=?");
            $query->execute(array($id));
            if($query->rowCount()==1){
                $post = $query->fetchObject();
                $response = $response->withJson(array('post' => $post, 200));
            }else{
                $message = array('message' => 'Publicação não encontrada');
                $response = $response->withJson($message,404);
            }
        }else{
            $message = array('message' => 'Identificador da publicação é inválido');
            $response = $response->withJson($message,500);    
        }
        
        return $response;
    }
    public function update(Request $request, Response $response, array $args){
        $params = $request->getParsedBody();
        if(!empty($params)){
            $id   = $args['id'];
            $title = $params['title'];
            $description = $params['description'];
            $user_id = $params['uuid'];
            // $files = $request->getUploadedFiles();
            $picture = 'picture.png';
            $query = $this->db->prepare("select id from post where id=?");
            $query->execute(array($id));
            if($query->rowCount()==1){
                $query = $this->db->prepare("update post set title=?, description=?, picture=? where id=? and user_id=?");
                $query->execute(array($title,$description,$picture,$id,$user_id));
                
                if($query->rowCount()==1){
                    $message = array('message' => 'Publicação atualizada com sucesso');
                    $response = $response->withJson($message,200);
                }else{
                    $message = array('message' => 'Erro na atualização da publicação');
                    $response = $response->withJson($message,500);
                }

            }else{
                $message = array('message' => 'Publicação não encontrada');
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
            $query = $this->db->prepare("select id from post where id=?");
            $query->execute(array($id));
            if($query->rowCount()==1){
                $query = $this->db->prepare("update post set status='Deleted' where id=?");
                $query->execute(array($id));
                if($query->rowCount()==1){

                }else{
                    $message = array('message' => 'Falha na exclusão da publicação');
                    $response = $response->withJson($message,500);
                }
            }else{
                $message = array('message' => 'Publicação não encontrada');
                $response = $response->withJson($message,404);
            }
        }else{
            $message = array('message' => 'Identificador da publicação é inválido');
            $response = $response->withJson($message,500);
        }
        
        return $response;
    }
    public function all_user(Request $request, Response $response, array $args){
        return $response;
    }
    public function all(Request $request, Response $response, array $args){
        return $response;
    }
    public function adopted(Request $request, Response $response, array $args){
        return $response;
    }
    public function peoples(Request $request, Response $response, array $args){
        return $response;
    }
}
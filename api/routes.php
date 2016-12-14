<?php 

$app->group('/user/{id}',function(){
    $this->get('/','\User:get');
    $this->put('/','\User:update');
    $this->delete('/','\User:delete');
});

$app->post('/user','\User:create');
$app->post('/user/auth','\User:auth');
$app->get('/user/logout','\User:logout');

$app->post('/post','\Post:create');
$app->get('/posts/{id}','\Post:all_user');
$app->get('/post/all','\Post:all');

$app->get('/post/adopted','\Post:adopted');

$app->group('/post/{id}',function(){
    $this->get('/','\Post:get');
    $this->put('/','\Post:update');
    $this->delete('/','\Post:delete');
    $this->get('/peoples','\Post:peoples');
});

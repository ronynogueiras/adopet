<?php 
$loader = require 'vendor/autoload.php';
error_reporting(E_ALL);
$dir    = __DIR__.'/adopet/src/classes';
$classes = scandir($dir);
foreach ($classes as $key => $class) {
    $info = new SplFileInfo($class);
    if ( $info->getExtension() === 'php'){
        include_once __DIR__.'/adopet/src/classes/'. $class;
    }
}

$config = require('config.php');

$app = new \Slim\App($config);

require 'dependencies.php';
require 'routes.php';

$app->run();
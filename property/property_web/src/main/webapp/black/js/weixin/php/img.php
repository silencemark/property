<?php
$img = file_get_contents('php://input', 'r'); 
$name = time().'.jpg';
file_put_contents($name, $img);
echo $name;

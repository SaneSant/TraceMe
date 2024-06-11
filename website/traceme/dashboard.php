<?php
include("config.php");
include("firebaseRDB.php");

$db = new firebaseRDB($databaseURL);
?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" integrity="sha512-+4zCK9k+qNFUR5X+cKL9EIR+ZOhtIloNl9GIKS57V1MyNsYpYcUrUeQc9vNfzsWfV28IaLL3i96P9sdNyeRssA==" crossorigin="anonymous" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
  <title>Admin | Folora</title>
</head>
<body>
<header>
  <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
      <a class="navbar-brand" href="dashboard.php">TraceMe</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item active">
            <a class="nav-link" href="dashboard.php">Home<span class="sr-only">(current)</span></a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="Chat.php">Messages</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="about.php">About</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="contact.php">Contact</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</header>
<br> 
<center>
<div class="container mt-3 ">
  <div class="row">
    <div class="col-sm-6 col-md-3">
      <div class="card text-center">
      <i class="fas fa-user fa-3x text-red mb-3 mt-3"></i>
              <div class="card-body">
          <h5 class="card-title">20+</h5>
          <p class="card-text">Total Users</p>
        </div>
      </div>
    </div>
   
    <div class="col-sm-6 col-md-3">
      <div class="card text-center">
      <i class="fas fa-bus fa-3x text-red mb-3 mt-3"></i>
              <div class="card-body">
          <h5 class="card-title">4+</h5>
          <p class="card-text">Total Bus Drivers</p>
        </div>
      </div>

  </div>
</div>
</center>
<br>
  <div class="container-fluid">
    <div class="row">
      <div class="col-md-6">
        <div class="card">
          <div class="card-header">
            Bus Drivers.
          </div>
          <div class="card-body">
            <table class="table">
              <thead>
                <tr>
               
                    <th scope="col">Email</th>
                    <th scope="col">Name</th>
                    <th scope="col">Root No</th>
          
                    <th scope="col">Delete</th>
                </tr>
              </thead>
              <tbody>
                <?php
                $data = $db->retrieve("Drivers");
                $data = json_decode($data, 1);

              
                
                if(is_array($data)){
                   foreach($data as $id => $bike){
                      echo "<tr>
                
                      <td>{$bike['emails']}</td>
                      <td>{$bike['fullname']}</td>
                  
                      <td>{$bike['phone']}</td>
                      <td><a href='b_d.php?id=$id' style='text-decoration:none;color:white; background:red;padding:10px;border-radius:5px'>DELETE</a></td>
                   </tr>";
                   }
                }
                ?>
              </tbody>
            </table>
          </div>
        </div>
      </div>
     
    </div>
    <br>

          
          

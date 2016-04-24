<!-- Author: Lorna Costelloe-->
<!DOCTYPE html>
<html>
	<head> 
		<meta charset="UTF-8" />
	   <link rel="stylesheet" type="text/css" media="all" href="/css/style.css" />
	    <title>Lockard Security</title>
	</head>
	<body>  
		<header>
		<div class ="logo">
			<img src ="/LOGO_PLACEHOLDER.PNG" alt="Lockard Security Logo">
			<img src ="/LOGO_PLACEHOLDER.PNG" alt="Lockard Security Logo">
			<img src ="/LOGO_PLACEHOLDER.PNG" alt="Lockard Security Logo">
			<img src ="/LOGO_PLACEHOLDER.PNG" alt="Lockard Security Logo">
		</div>
	    <div class="nav">
	      <ul>
	        <li class="home"><a href="/hello.php">Home</a></li>
	        <li class="products"><a href="/products.php">Products</a></li>
	        <li class="about"><a href="/about.php">About Us</a></li>
	        <li class="contact"><a class="active" href="/contact.php">Contact Us</a></li>
	        <li class="signin"><a href="/login.php">Log In</a></li>
	      </ul>
	    </div>
	    
	    <div id="page-wrap">

		<img src = "/lockard_logo.png">
		
		<p>Ask for dimension so as to resize correctly.</p>
		
		<p>Please fill out the form below to contact us.</p>
				
		<div id="contact-area">
			
			<form method="post" action="contactengine.php">
			
				<label for "reason">Reason:</label>
	             	<select id = "myList">
		               <option value = "buy">I want to buy a product</option>
		               <option value = "question">I have a question regarding a product.</option>
		               <option value = "payment">I need to make a payment.</option>
		               <option value = "details">I need to reset my password/log in.</option>
	           	  </select>
	           	  <br><br><br>
				<label for="Name">Name:</label>
				<input type="text" name="Name" id="Name" />
	
				<label for="Email">Email:</label>
				<input type="text" name="Email" id="Email" />
				
				<label for="acnumber">Household A/C Number:</label>
				<input type="text" name="acnumber" id="acnumber" />
				
				<label for="Message">Message:</label><br />
				<textarea name="Message" rows="20" columns="20" id="Message"></textarea>

				<input type="submit" name="submit" value="Submit" class="submit-button" />
			</form>
			
			<div style="clear: both;"></div>
		</div>
	
	  </header>
	  
	      
  </body>
	<footer></footer>
</html>
<!-- Author: Lorna Costelloe-->
<!DOCTYPE html>
<html>
	<head> 
		<meta charset="UTF-8" />
	   <link rel="stylesheet" type="text/css" media="all" href="/css/style.css" />
	    <title>Lockard Security</title>
	    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
	</head>
	<body>  
		<header>
		<div class ="logo">
			<img src ="/LOGO_PLACEHOLDER.PNG" alt="Lockard Security Logo">
		</div>
	    <div class="nav">
	      <ul>
	        <li class="home"><a href="/hello.php">Home</a></li>
	        <li class="products"><a href="/products.php">Products</a></li>
	        <li class="about"><a href="/about.php">About Us</a></li>
	        <li class="contact"><a href="/contact.php">Contact Us</a></li>
	        <li class="signin"><a class="active" href="/login.php">Log In</a></li>
	      </ul>
	    </div>
	  </header>
	  <br><br><br><br>
	<div class="tabs">
	   <ul>
	    <li class="current"><a href="#HEATING">Heating</a></li>
	    <li><a href="#H_ALARM">House Alarm</a></li>
	    <li><a href="#F_ALARM">Fire Alarm</a></li>
	    <li><a href="#C_ALARM">Carbon Monoxide Alarm</a></li>
	   </ul>
	  <div>
	   <div id="HEATING" class="tab-content">
	   		<form action="" method="post">
			  <input type="checkbox" name="heating" value="On" /> Turn my heating on
			  <br>
			  <input type="checkbox" name="heating" value="updateYes" /> I want updates on this.
			 <br><br>
			 <p>Heating Time Settings</p>        
		   	 	<input type="radio" name="hourSet" value="12"> 12 hour
			 	<input type="radio" name="hourSet" value="24"> 24 hour
			 <br><br>
			 <form action="demo_form.asp">
  				Keep house at <input type="number" name="temperature"> &deg; C at all times.<br>
  			</form>
  			<br><br>
	   	    <a href="#" class="submit">Apply Changes</a>
			</form>
	   </div>
	   
	   <div id="H_ALARM" class="tab-content">
	  		 <form action="" method="post">
			  <input type="checkbox" name="H_ALARM" value="Halert" /> Alert me if my house alarm turns on
			  <br><br>
	   	    <a href="#" class="submit">Apply Changes</a>
			</form>
	    </div>
	    
	   <div id="F_ALARM" class="tab-content">
	   		<form>
	  		 <input type="checkbox" name="F_ALARM" value="Falert" /> Alert me if my fire alarm turns on
	  		 <br><br>
	   	    <a href="#" class="submit">Apply Changes</a>
	  		</form>
	   </div>
	   
	   <div id="C_ALARM" class="tab-content">
	   	  <form>
	   		<input type="checkbox" name="C_ALARM" value="Calert" /> Alert me if my Carbon Monoxide alarm turns on
	   	    <br><br>
	   	    <a href="#" class="submit">Apply Changes</a>
	   	  </form>
	   	</div>
	  </div>
	 </div>
	 
		 <script type="text/javascript">
	
			$(document).ready(function(){
			 $(".tabs li").click(function() {
			  $(this).parent().parent().find(".tab-content").hide();
			  var selected_tab = $(this).find("a").attr("href");
			  $(selected_tab).fadeIn();
			  $(this).parent().find("li").removeClass('current');
			  $(this).addClass("current");
			   return false;
			    });
			});
		  </script>
  </body>
	<footer></footer>
</html>
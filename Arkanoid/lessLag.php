<html>
	<title>Arkanoid</title>

	<body>		
		
		<?php
		// for integers
		function powerMod ($A, $E, $N) {
		if ($E % 1 == 0) {
			if ($E == 1) {
				return $A;
			} else {
			return powerMod($A,$E-1,$N)*$A%$N;	
			}
		} else return "Error";
		}
		
		function modinverse ($A, $Z) {
		    $u1=1;
		    $u2=0;
		    $u3=$A;
		    $v1=0;
		    $v2=1;
		    $v3=$Z;
			while ($v3 != 0){
				$qq=floor($u3/$v3);
				$t1=$u1-$qq*$v1;
				$t2=$u2-$qq*$v2;
				$t3=$u3-$qq*$v3;
				$u1=$v1;
				$u2=$v2;
				$u3=$v3;
				$v1=$t1;
				$v2=$t2;
				$v3=$t3;
				$z=1;
			}				
			$uu=$u1;
			$vv=$u2;
			if ($uu<0){
				$I=$uu+$Z;
			}else{
				$I=$uu;
			}			
			return $I;
		}
		
		// 20 primes : 400 possibilities
		$primes = array(151,157,163,167,173,179,181,191,193,197,199,211,223,227,229,233,239,241,251,257);
		
		/*
		$p = rand(0,19);
		$q = rand(0,19);
		while ($q == $p)
		{
		$q = rand(0,19);
		}
		$p = $primes[$p];
		$q = $primes[$q];
		*/
		$p = 127;
		$q = 251;
		$key = $p*$q;
		$exp = 17;
		$privateExp = modinverse($exp,($p-1)*($q-1));
		
		$score = $_GET["score"];
		$encryptedScore = $_GET["scoreEncryption"];
		$name = $_GET["name"];
		$name = substr($name,0,8);
		/*
		$key = $_GET["key"];
		$exp = $_GET["key2"];
		*/
		
		// do in java applet
		$encryptedScoreCheck = powerMod($score/10,$exp,$key);
		
		// decryption to ensure that key is for score
		$scoreCheck = powerMod($encryptedScore,$privateExp,$key)*10;						
		
		$host = 'localhost';
		$dbname = 'qqnoob5_ArkanoidHighScores';
		$uname = 'qqnoob5_frecka';
		$passwd = 'yOg7Fcz2dFVJ'; 
		$table = 'HighScores';
		
		$con = mysql_connect($host,$uname,$passwd);
		if (!$con)
		{
			die('Could not connect: ' . mysql_error());
		}
		
		mysql_select_db($dbname, $con);
		
		$sql = "CREATE TABLE HighScores
		(
		Rank int NOT NULL AUTO_INCREMENT,
		PRIMARY KEY(Rank),
		Score int(10)
		)";
		
		//mysql_query($sql,$con);
		
		//mysql_query("INSERT INTO HighScores (Score)
		//VALUES ('0')");				
				
		
		$result = mysql_query("SELECT * FROM HighScores ORDER BY Score DESC");
		
		//$row = mysql_fetch_array($result);
		
		//$highScore = $row['Score'];
		$highScoreArray = array(0,0,0,0,0);
		$names = array(0,0,0,0,0);
		
		$i = 0;
		
		while($row = mysql_fetch_array($result))
			  {
			  $highScoreArray[$i] = $row['Score'];
			  $names[$i] = $row['Name'];
			  //echo $row['Score'];			  
			  //echo "<br />";
			  $i++;			  
			  }
		//print_r($highScoreArray);
		
			
		$newHighScore = false;
		
		$msg = " ";
		$displayMsg = false;
		
		//echo $score;
		$unique = true;
		for ($i = 0; $i < 5; $i++)
			{
			if ($score == $highScoreArray[$i]) 
			{
			$unique = false;
			//echo "got one";
			}
			//echo $highScoreArray[$i];
			}
		
		if ($score == $scoreCheck && $encryptedScore == $encryptedScoreCheck)
		// valid score, only sent if it beats a score
		{
			if ($score > $highScoreArray[4] && $unique) {
				$highScore = $score;
				$newHighScore = true;
				}
		} elseif ($score > 0)
		{
		$msg = "Invalid encrypted score. <p>";
		$displayMsg = true;
		}
		
		if ($newHighScore)
		{
		mysql_query("INSERT INTO HighScores (Score, Name)
		VALUES ('".$highScore."','".$name."')");
		
				
		$result2 = mysql_query("SELECT * FROM HighScores ORDER BY Score ASC");
		$row = mysql_fetch_array($result2); //will be smallest score
		$lowScore = $row['Score'];
		mysql_query("DELETE FROM HighScores WHERE `HighScores`.`Score` = '".$lowScore."' LIMIT 1");
		
		//redo highscore listing
		$result3 = mysql_query("SELECT * FROM HighScores ORDER BY Score DESC");
		
		//$row = mysql_fetch_array($result);
		
		//$highScore = $row['Score'];
		$highScoreArray = array(0,0,0,0,0);
		$names = array(0,0,0,0,0);
		$i = 0;
		
		while($row = mysql_fetch_array($result3))
			  {
			  $highScoreArray[$i] = $row['Score'];			  
			  $names[$i] = $row['Name'];
			  
			  //echo $row['Score'];			  
			  //echo "<br />";
			  $i++;			  
			  }
		}
		
		
		mysql_close($con);

		
		//$result = mysql_query("SELECT * FROM HighScores");
		
		//echo $result;
		/*
		while($row = mysql_fetch_array($result))
			  {
			  echo $row['FirstName'] . " " . $row['LastName'];
			  echo "<br />";
			  }
			  */
			  
		$width = $_GET["width"];
		$height = $_GET["height"];
		
		$widthSet = "yes";
		$heightSet = "yes";
		
		if (!$width > 0 || !$height > 0)
			{
			$width = 775;
			$height = 569;
			$widthSet = "no";
			$heightSet = "no";
			}
		
		
			  
		?>
			
	
	
		<applet code="ArkanoidAppletLessLag.class" width="<?php echo $width ?>" height="<?php echo $height ?>" MAYSCRIPT>
		<PARAM name="prevHighScore0" value="<?php echo $highScoreArray[0] ?>">
		<PARAM name="prevHighScore1" value="<?php echo $highScoreArray[1] ?>">
		<PARAM name="prevHighScore2" value="<?php echo $highScoreArray[2] ?>">
		<PARAM name="prevHighScore3" value="<?php echo $highScoreArray[3] ?>">
		<PARAM name="prevHighScore4" value="<?php echo $highScoreArray[4] ?>">
		<PARAM name="prevHighScore5" value="<?php echo $highScoreArray[5] ?>">
		<PARAM name="prevHighScore6" value="<?php echo $highScoreArray[6] ?>">
		<PARAM name="prevHighScore7" value="<?php echo $highScoreArray[7] ?>">
		<PARAM name="prevHighScore8" value="<?php echo $highScoreArray[8] ?>">
		<PARAM name="prevHighScore9" value="<?php echo $highScoreArray[9] ?>">
		<PARAM name="name0" value="<?php echo $names[0] ?>">
		<PARAM name="name1" value="<?php echo $names[1] ?>">
		<PARAM name="name2" value="<?php echo $names[2] ?>">
		<PARAM name="name3" value="<?php echo $names[3] ?>">
		<PARAM name="name4" value="<?php echo $names[4] ?>">
		<PARAM name="name5" value="<?php echo $names[5] ?>">
		<PARAM name="name6" value="<?php echo $names[6] ?>">
		<PARAM name="name7" value="<?php echo $names[7] ?>">
		<PARAM name="name8" value="<?php echo $names[8] ?>">
		<PARAM name="name9" value="<?php echo $names[9] ?>">
		<PARAM name="widthSet" value="<?php echo $widthSet ?>">
		<PARAM name="heightSet" value="<?php echo $heightSet ?>">
		<!--
		<PARAM name="mult" value="<?php //echo $mult ?>">
		
		<PARAM name="p" value="<?php //echo $p ?>">
		<PARAM name="q" value="<?php //echo $q ?>">
		-->
		


		</applet>		
		<br>
		
		<?php 
		if ($displayMsg) 
		{
		echo $msg;
		}		
		?>


		
		If you click outside of the screen, click back inside it to enable the controls again.
		<br>
		If the game is STILL lagging for you, yell at me.
		<br>
		<H2>Controls:	</H2>
		<p>		
		<TABLE Border=2 CELLPADDING=3>
		<TR> <TH>Left and Right arrow keys: </TH>  <TD> 	Move the paddle </TD>  </TR>
		<TR> <TH>X: </TH>  <TD>	Release the ball, fire laser, let go of ball when you have sticky powerup (green) </TD>  </TR>
		<TR> <TH>R: </TH>  <TD>	Tap to curve ball left when you have the god mode powerup (purple) </TD>  </TR>
		<TR> <TH>T: </TH>  <TD>	Tap to curve ball right when you have the god mode powerup (purple) </TD>  </TR>
		<TR> <TH>Esc: </TH>  <TD>	Sacrifice your current life and restart the stage  </TD>  </TR>  </TR>
		<TR> <TH>Ctrl: </TH>  <TD>	Hold Ctrl to move your paddle more slowly  </TD>  </TR>
		</TABLE>		
		<p>		
		<H2>Powerups:	</H2>	
		<p>		
		<TABLE Border=2 CELLPADDING=3>
		<TR> <TH>Color:	 </TH>  <TH>  Power: </TH>  </TR>
		<TR> <TD>Blue	</TD>  <TD>		Bigger paddle </TD>  </TR>
		<TR> <TD>Gray	</TD>  <TD>		Smaller paddle </TD>  </TR>
		<TR> <TD>Green	</TD>  <TD>		Sticky paddle </TD>  </TR>
		<TR> <TD>Pink	</TD>  <TD>		Slowed ball </TD>  </TR>
		<TR> <TD>Purple	</TD>  <TD>		God Mode (see controls) </TD>  </TR>
		<TR> <TD>Red	</TD>  <TD>		Laser </TD>  </TR>
		<TR> <TD>Cyan	</TD>  <TD>		Split 1 ball into 3 balls </TD>  </TR>
		<TR> <TD>Gold	</TD>  <TD>		Unstoppable ball (doesn't bounce off blocks, just destroys them) </TD>  </TR>
		</TABLE>
		<p>
		<H2> More Information: </H2>
		<p>
		Objective: Destroy all the blocks in each level without running out of lives. <br>
		There are currently a total of 12 stages. <br>
		You also get points for destroying blocks, getting powerups, and completing stages  
			so try and beat my high score of 120640. <br>
		Every 25000 points you'll get an extra life. <br>
		Top 10 high scores are now saved, along with a name up to 8 letters long.
		<p>
		Silver blocks take two hits to destroy. <br>
		Gold blocks can't be destroyed (unless you have the unstoppable ball powerup). <br>
		You can only have one powerup at a time. <br>
		You'll also probably notice that the ball eventually speeds up, and this happens because of a
		slight increase in speed every time the ball bounces off something.
		<p>
		This was my final project for an introductory computer science class, CSCI 134, taken at Williams College in the fall of 2008.<br>
		Based off of the original Arkanoid game, which you can see at 
		<a href="http://www.nintendo8.com/game/37/arkanoid/"> http://www.nintendo8.com/game/37/arkanoid/ </a>
		<p>
		Also, if you feel like playing around with some interactive physics simulations I programmed, go to: <br>
		<a href="http://cfb.qqnoobs.com/Balls/"> Colliding balls (gravity and charge) </a>	or
		<a href="http://cfb.qqnoobs.com/SolarSystem/"> Solar System </a>
		<p>
		Finally, after giving a math colloquium talk on fractals I became interested in generating them, and <br>
		programmed this interactive fractal generator which can be fun to play around with: <br>
		<a href="http://cfb.qqnoobs.com/Fractals/"> Frecktal </a> <br>
		With a little practice you can change the starting triangle into a fern. (This program is in many ways based <br>
		off of the program TeraFractal, a more professional one found at <a href="http://www.terafractal.com/">http://www.terafractal.com/</a>, <br>
		but since I don't have a Mac, I wanted to make a version that would work on my PC.) 
		
		<p>
		Questions, comments, or suggestions? Email me at 09cfb@williams.edu
		<p>
		Last updated 2/24/09. (Added round system: after beating all 12 stages (2 new stages), go <br>
			back to stage 1 and play with a smaller paddle and faster ball. <br>
			Fixed round system so that ball speed increases by 3/2, paddle size decreases to 2/3 original size.)		
		
		
		
		<?php //"score: ".$score; ?> 
		<?php //echo "encrypted score: ".$encryptedScore; ?>
		<?php //echo "encrypted score check: ".$encryptedScoreCheck;?>	
		<?php //echo "decrypted score: ".$scoreCheck; ?>
		
		
	</body>

</html>
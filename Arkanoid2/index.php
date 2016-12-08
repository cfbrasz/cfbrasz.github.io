<html>
	<title>Arkanoid 2.0</title>

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
		
		$mult = $_GET["mult"];
		if(empty($mult)) $mult = 10;
		/*
		$key = $_GET["key"];
		$exp = $_GET["key2"];
		*/
		
		// do in java applet
		$encryptedScoreCheck = powerMod($score/10,$exp,$key);
		
		// decryption to ensure that key is for score
		$scoreCheck = powerMod($encryptedScore,$privateExp,$key)*10;						
		
		$host = 'localhost';
		$dbname = 'qqnoob5_Arkanoid2HighScores';
		$uname = 'qqnoob5_frecka';
		$passwd = 'yOg7Fcz2dFVJ'; 
		$table = 'HighScores2';
		
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
		$highScoreArray = array(0,0,0,0,0,0,0,0,0,0);
		$names = array(0,0,0,0,0,0,0,0,0,0);
		
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
		for ($i = 0; $i < 10; $i++)
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
			if ($score > $highScoreArray[9] && $unique) {
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
		$highScoreArray = array(0,0,0,0,0,0,0,0,0,0);
		$names = array(0,0,0,0,0,0,0,0,0,0);
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
			
	
	
		<applet code="ArkanoidApplet.class" width="<?php echo $width ?>" height="<?php echo $height ?>" MAYSCRIPT>
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


			
		<br>		
		If you click outside of the screen, click back inside it to enable the controls again. Also, changing the font size of this page will mess up the layout of the game.
		<br>
		
		<br>
		<H2>Controls:	</H2>
		<p>		
		<TABLE Border=2 CELLPADDING=3>
		<TR> <TH>Left and Right arrow keys: </TH>  <TD> 	Move the paddle </TD>  </TR>
		<TR> <TH>X: </TH>  <TD>	Release the ball, fire laser or missile, let go of ball when you have sticky powerup (green) </TD>  </TR>
		<TR> <TH>Ctrl: </TH>  <TD>	Hold to slow paddle movement (for finer control) </TD>  </TR>
		<TR> <TH>+ (not on num pad): </TH>  <TD>	Press (and hold) to speed all balls up (after collisions you might be left with just a really slow ball) </TD>  </TR>
		<TR> <TH>R: </TH>  <TD>	Press (and hold) to curve ball left when you have the god mode powerup (purple) </TD>  </TR>
		<TR> <TH>T: </TH>  <TD>	Press (and hold) to curve ball right when you have the god mode powerup (purple) </TD>  </TR>
		<TR> <TH>Esc: </TH>  <TD>	Sacrifice your current life and restart the stage  </TD>  </TR>
		</TABLE>		
		<p>		
		<H2>Powerups:	</H2>	
		<p>		
		<TABLE Border=2 CELLPADDING=3>
		<TR> <TH>Color:	 </TH>  <TH>  Power: </TH>  </TR>
		<TR> <TD>Blue	</TD>  <TD>		Bigger, flatter paddle </TD>  </TR>
		<TR> <TD>Dark Blue	</TD>  <TD>	Split all blocks in two </TD>  </TR>
		<TR> <TD>Gray	</TD>  <TD>		Smaller paddle </TD>  </TR>
		<TR> <TD>Green	</TD>  <TD>		Sticky paddle (release with X) </TD>  </TR>
		<TR> <TD>Yellow	</TD>  <TD>		Bigger balls </TD>  </TR>
		<TR> <TD>Purple	</TD>  <TD>		God Mode (see controls) </TD>  </TR>
		<TR> <TD>Red	</TD>  <TD>		Laser (fire with X) </TD>  </TR>
		<TR> <TD>Dark Green	</TD>  <TD>	Missile (fire with X, destroys all blocks within circle around contact) </TD>  </TR>
		<TR> <TD>Cyan	</TD>  <TD>		Split 1 ball into 3 balls </TD>  </TR>
		<TR> <TD>White	</TD>  <TD>		Extra points per block (100 instead of 70), but increased curvature from spin </TD>  </TR>
		<TR> <TD>Gold	</TD>  <TD>		Unstoppable ball (doesn't bounce off blocks, just destroys them) </TD>  </TR>
		</TABLE>
		<p>
		<H2> More Information: </H2>
		<p>
		Objective: Destroy the blocks in each level without running out of lives. <br>
		There are a total of 18 stages. <br>
		You get points for destroying blocks (70 per block), getting powerups (150 per powerup), and completing stages (1000 per level).<br>
		<p>
		Silver blocks take two hits to destroy. <br>
		Gold blocks can't be destroyed (unless you have the unstoppable ball powerup or a missile) and they don't need to be destroyed to complete a stage. <br>
		You can only have one powerup at a time. <br>
		A ball's speed increases every time it bounces off something.
		<p>
		This is an updated version of my other Arkanoid game, which is at <a href="http://cfb.qqnoobs.com/Arkanoid/">http://cfb.qqnoobs.com/Arkanoid/</a><br>
		Based off of the original Arkanoid game, which you can see at 
		<a href="http://www.nintendo8.com/game/37/arkanoid/"> http://www.nintendo8.com/game/37/arkanoid/ </a>
		<p>
		<p>
		Questions, comments, or suggestions? Email me at cfbrasz@gmail.com
		<p>
		<a href="http://cfb.qqnoobs.com/">Home</a>
		<p>
		Last updated 9/9/13.
		
		<?php //"score: ".$score; ?> 
		<?php //echo "encrypted score: ".$encryptedScore; ?>
		<?php //echo "encrypted score check: ".$encryptedScoreCheck;?>	
		<?php //echo "decrypted score: ".$scoreCheck; ?>
		
		
	</body>

</html>
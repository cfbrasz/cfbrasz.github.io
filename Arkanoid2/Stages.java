//
// Class for stages in Arkanoid 2.0
// (c) Fred Brasz 2009

public interface Stages {

	public static final String s1 = "0 0 0 s s s s s 0 0 0 " +
				"0 b b b b b b b b b 0 " +
				"g g g g g g g g g g g " +
				"r r r r r r r r r r r " +
				"0 y y y y y y y y y 0 " +
				"0 0 0 p p p p p 0 0 0 ";
				
	public static final String s2 = "0 i p s r 0 o y g b 0 " +
				"0 p s r o 0 y g b i 0 " +
				"0 s r o y 0 g b i p 0 " +
				"0 r o y g 0 b i p s 0 " +
				"0 o y g b 0 i p s r 0 " +
				"0 y g b i 0 p s r o 0 " +
				"0 g b i p 0 s r o y 0 " +
				"0 b i p s 0 r o y g 0 " +
				"0 i p s r 0 o y g b 0 " +
				"0 p s r o 0 y g b i 0 " +
				"0 s r o y 0 g b i p 0 " +
				"0 r o y g 0 b i p s 0 " +
				"0 s s s s 0 s s s s 0 ";
				
	public static final String s3 = "q 0 q 0 q 0 q 0 q 0 q " +
				"0 q 0 q 0 q 0 q 0 q 0 " +
				"q 0 q 0 q 0 q 0 q 0 q " +
				"0 q 0 q 0 q 0 q 0 q 0 " +
				"q 0 q 0 q 0 q 0 q 0 q " +
				"0 q 0 q 0 q 0 q 0 q 0 " +
				"q 0 q 0 q 0 q 0 q 0 q " +
				"0 q 0 q 0 q 0 q 0 q 0 " +
				"q 0 q 0 q 0 q 0 q 0 q " +
				"0 q 0 q 0 q 0 q 0 q 0 " +
				"q 0 q 0 q 0 q 0 q 0 q " +
				"0 q 0 q 0 q 0 q 0 q 0 " +
				"q 0 q 0 q 0 q 0 q 0 q " +
				"0 q 0 q 0 q 0 q 0 q 0 " +
				"q 0 q 0 q 0 q 0 q 0 q " +
				"0 q 0 q 0 q 0 q 0 q 0 " +
				"q 0 q 0 q 0 q 0 q 0 q " +
				"0 q 0 q 0 q 0 q 0 q 0";
				
	public static final String s4 = "0 0 0 r r r r r 0 0 0 " +
				"0 0 r r 0 0 0 r r 0 0 " +
				"0 0 r 0 0 0 0 0 r 0 0 " +
				"0 r r 0 b b b 0 o o 0 " +
				"0 r 0 0 b 0 b 0 0 o 0 " +
				"r r 0 b b 0 b b 0 o 0 " +
				"r 0 0 b 0 0 0 b 0 o o " +
				"r 0 0 b b b 0 b 0 0 o " +
				"r r 0 0 b 0 0 p 0 o o " +
				"0 r 0 0 0 0 0 p 0 o 0 " +
				"0 r p 0 0 0 p p 0 o 0 " +
				"0 0 p p 0 p p 0 0 o 0 " +
				"0 0 0 p p p 0 0 o o 0 " +
				"0 y 0 0 0 0 0 0 y 0 0 " +
				"0 y y y 0 0 0 y y 0 0 " +
				"0 0 0 y y y y y 0 0 0 " +
				"= 0 = 0 = 0 = 0 = 0 =";
				
	public static final String s5 = "0 0 r 0 0 0 0 0 r 0 0 " +
				"c 0 r r 0 r 0 r r 0 c " +
				"b 0 r y r r r y r 0 b " +
				"0 t 0 r y o y r 0 t 0 " +
				"0 l 0 o y y y o 0 l 0 " +
				"0 0 c 0 o y o 0 c 0 0 " +
				"0 0 b 0 b b b 0 b 0 0 " +
				"0 0 0 t 0 w 0 t 0 0 0 " +
				"0 0 0 l 0 0 0 l 0 0 0 " +
				"y y 0 0 c 0 c 0 0 y y " +
				"y g y 0 b 0 b 0 y g y " +
				"o y g y 0 t 0 y g y o " +
				"y o r = 0 l 0 = r o y " +
				"r p k = 0 s 0 = k p r " +
				"o y = 0 0 b 0 0 = y o " +
				"= = 0 0 s 0 s 0 0 = = " +
				"0 0 0 0 s 0 s 0 0 0 0 " +
				"0 0 0 s 0 0 0 s 0 0 0 " +
				"0 0 0 s 0 0 0 s 0 0 0 " +
				"0 0 s 0 0 0 0 0 s 0 0 " +
				"0 0 s 0 0 0 0 0 s 0 0 " +
				"s s 0 0 0 0 0 0 0 s s ";
				
	public static final String s6 = "0 0 0 0 0 = 0 0 0 0 0 " +
				"0 0 0 0 0 = 0 0 0 0 0 " +
				"0 0 0 0 0 = 0 0 0 0 0 " +
				"0 0 0 0 y y = 0 0 0 0 " +
				"0 0 0 0 y y = 0 0 0 0 " +
				"0 0 0 0 y y = 0 0 0 0 " +
				"0 0 0 y y y y = 0 0 0 " +
				"0 0 0 y y y y = 0 0 0 " +
				"0 0 0 y y y y = 0 0 0 " +
				"0 0 = 0 0 0 0 0 = 0 0 " +
				"0 0 = 0 0 0 0 0 = 0 0 " +
				"0 0 = 0 0 0 0 0 = 0 0 " +
				"0 y y = 0 0 0 y y = 0 " +
				"0 y y = 0 0 0 y y = 0 " +
				"0 y y = 0 0 0 y y = 0 " +
				"y y y y = 0 y y y y = " +
				"y y y y = 0 y y y y = " +
				"y y y y y = y y y y =";
				
	public static final String s7 = "0 0 0 r r r r r 0 0 0 " +
				"0 0 r r r r r r r 0 0 " +
				"0 0 r r r r r r r 0 0 " +
				"0 r r r r r r r r r 0 " +
				"0 r r r r r r r r r 0 " +
				"0 r r r r r r r r r 0 " +
				"r r r r r r r r r r r " +
				"r r r r r 0 r r r r r " +
				"r r r r 0 0 0 r r r r " +
				"r r r 0 0 s 0 0 r r r " +
				"r r r 0 s w s 0 r r r " +
				"0 0 0 0 s w s 0 0 0 0 " +
				"w w w 0 s w s 0 w w w " +
				"w w w 0 0 s 0 0 w w w " +
				"w w w w 0 0 0 w w w w " +
				"w w w w w 0 w w w w w " +
				"w w w w w w w w w w w " +
				"0 w w w w w w w w w 0 " +
				"0 w w w w w w w w w 0 " +
				"0 w w w w w w w w w 0 " +
				"0 0 w w w w w w w 0 0 " +
				"0 0 w w w w w w w 0 0 " +
				"0 0 0 w w w w w 0 0 0";
				
	public static final String s8 = "p p p p 0 s 0 t t t t " +
				"k k k k 0 s 0 l l l l " +
				"p p p 0 0 s 0 0 t t t " +
				"k k k 0 s y s 0 l l l " +
				"p p p 0 s y s 0 t t t " +
				"k k 0 0 s y s 0 0 l l " +
				"p p 0 s y 0 y s 0 t t " +
				"k k 0 s y 0 y s 0 l l " +
				"p 0 0 s y 0 y s 0 0 t " +
				"k 0 s y 0 0 0 y s 0 l " +
				"p 0 s y 0 = 0 y s 0 t " +
				"0 0 s y 0 = 0 y s 0 0 " +
				"0 s y 0 0 = 0 0 y s 0 " +
				"0 s y 0 = 0 = 0 y s 0 " +
				"0 s y 0 = 0 = 0 y s 0 " +
				"s y 0 0 = 0 = 0 0 y s " +
				"s y 0 = 0 0 0 = 0 y s " +
				"s y 0 = 0 0 0 = 0 y s";
				
	public static final String s9 = "0 0 0 0 0 s s 0 0 0 0 " +
				"0 0 0 s s p p s 0 0 0 " +
				"0 0 s p s p p s s 0 0 " +
				"0 0 s p p s s p p s 0 " +
				"0 s p p p s p p p s 0 " +
				"0 s p p p p s p p p s " +
				"s p p p p p s p p p s " +
				"s s s s s s s s s s s " +
				"0 0 0 0 0 0 0 0 0 0 0 " +
				"0 p 0 p p 0 0 0 p p 0 " +
				"p p 0 p p 0 0 p p p p " +
				"p 0 0 0 p p 0 p p 0 p " +
				"p 0 p 0 0 p 0 p 0 0 p " +
				"p 0 p 0 0 p 0 p p 0 p " +
				"p p p p 0 p p p 0 0 p " +
				"0 p 0 p p p p p 0 0 p " +
				"0 0 0 p p 0 p 0 0 p p " +
				"0 0 0 0 0 0 0 0 p p p " +
				"0 0 0 0 0 0 0 0 0 p 0";
				
	public static final String s10 = "b b b b y = b b b b b " +
				"b b b b y = b b b b b " +
				"b b b b y = b b b b b " +
				"b b b b y = b b b b b " +
				"b b b b y = b b b b b " +
				"b b b b y = b b b b b " +
				"y y y y y = y y y y y " +
				"= = = = y = = = = = = " +
				"b b b b y = b b b b b " +
				"b b b b y = b b b b b " +
				"b b b b y = b b b b b " +
				"b b b b y = b b b b b " +
				"b b b b y = b b b b b " +
				"b b b b y = b b b b b ";
				
	public static final String s11 = "b b b = = = = = = = = " +
				"0 0 0 0 0 0 0 0 0 0 0 " +
				"m m m m m m m m m m m " +
				"0 0 0 0 0 0 0 0 0 0 0 " +
				"= = = = = = = = r r r " +
				"0 0 0 0 0 0 0 0 0 0 0 " +
				"p p p p p p p p p p p " +
				"0 0 0 0 0 0 0 0 0 0 0 " +
				"g g g = = = = = = = = " +
				"0 0 0 0 0 0 0 0 0 0 0 " +
				"c c c c c c c c c c c " +
				"0 0 0 0 0 0 0 0 0 0 0 " +
				"= = = = = = = = r r r";		
				
	public static final String s12 = "q q q q q 0 0 0 0 = p " +
				"q q q q q 0 0 = 0 0 = " +
				"q q q q 0 0 q = 0 0 = " +
				"q q q q 0 0 q = 0 0 = " +
				"q q q 0 0 0 q q = 0 = " +
				"q q q 0 0 q q q = 0 = " +
				"q q q 0 0 q q = 0 0 = " +
				"q q q q q q q = 0 0 0 " +
				"q q q q q q q = 0 = q " +
				"q q q q q q = 0 0 = q " +
				"q q q q q q = 0 0 = q " +
				"q q q q q q = 0 = q q " +
				"= = = = = = 0 0 = = =";
				
	public static final String s0 = "0 0 0 0 0 0 0 0 0 = 0 " +
				"0 0 0 0 0 0 0 0 = 0 0 " +
				"0 0 0 0 0 0 0 = 0 0 0 " +
				"0 0 0 0 0 0 = 0 0 0 0 " +
				"0 0 0 0 0 = 0 0 0 0 0 " +
				"0 0 0 0 = 0 0 0 0 0 0 " +
				"0 0 0 0 = 0 0 0 0 0 0 " +
				"0 0 0 0 = 0 0 0 0 0 0 " +
				"0 0 0 0 = 0 0 0 0 0 0 " +
				"0 0 0 0 = 0 0 0 0 0 0 " +
				"0 0 0 0 = 0 0 0 0 0 0 " +
				"0 0 0 0 = 0 0 0 0 0 0 " +
				"0 0 0 0 = 0 0 0 0 0 0 " +
				"0 0 0 0 = 0 0 0 0 0 0 " +
				"0 0 0 0 = 0 0 0 0 0 0 " +
				"0 0 0 0 = 0 0 0 0 0 0 " +
				"0 0 0 0 = 0 0 0 0 0 0 " +
				"0 0 0 0 = 0 0 0 0 0 0 " +
				"= = = = = = = = = = = " +
				"b 0 b 0 b 0 b 0 b 0 b " +
				"s s s s s s s s s s s ";
	
	
}
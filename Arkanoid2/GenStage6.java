import java.awt.*;

public interface GenStage6 {

public static final int nBlocks = 660;

public static final int blocksPerRow = 22;
public static final double heightWidthRatio = 0.5;
public static final Color bgColor = new Color(0,0,0);

public static final int[] r = {142,143,143,143,143,143,143,143,143,143,143,142,194,163,142,143,143,143,143,143,143,143,142,143,143,143,143,143,143,143,143,143,143,142,192,160,143,143,143,143,143,143,143,143,142,143,143,143,143,143,143,143,143,143,143,142,208,175,142,143,143,143,143,143,143,143,142,143,143,143,143,143,143,143,143,143,143,142,139,136,142,143,143,143,143,143,143,143,142,143,143,143,143,143,143,143,143,143,143,142,143,143,143,143,143,143,143,143,143,143,142,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,142,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,142,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,142,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,142,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,142,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,142,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,142,143,143,142,142,143,143,143,142,142,142,142,143,142,142,143,142,142,143,143,143,143,142,143,146,211,159,143,143,143,143,154,177,204,150,174,210,146,147,143,143,143,143,143,142,143,148,189,158,143,143,143,144,142,181,172,137,178,180,131,138,143,143,143,143,143,142,143,148,217,162,143,143,143,144,142,179,206,137,174,216,132,138,143,143,143,143,143,142,143,140,126,133,143,143,143,143,118,120,124,117,120,125,116,127,142,143,143,143,143,142,143,143,143,143,143,143,143,142,143,143,143,143,143,143,143,143,143,143,143,143,143,142,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,142,142,142,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,107,142,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,143,142,91,142,142,143,143,142,143,142,143,143,143,142,142,143,143,143,143,142,143,143,143,142,94,129,142,143,143,84,40,139,143,143,142,146,142,143,143,142,132,140,143,143,143,142,75,106,130,143,134,9,4,90,142,143,142,186,145,143,143,143,116,119,142,143,143,143,96,123,114,142,76,0,0,23,141,142,143,164,143,142,143,131,120,117,135,142,143,142,96,139,136,137,36,24,21,22,114,150,153,185,154,150,151,133,140,139,133,152,149,150,111,182,186,166,184,183,165,189,181,163,189,174,168,181,183,176,183,175,178,190,167,179,189,181,179,168,180,178,166,182,178,165,181,180,172,172,182,179,173,175,180,178,168,181,180,156,151,155,156,152,152,158,153,149,160,155,154,153,156,159,146,158,160,143,160,160,141,176,181,169,177,180,167,180,179,165,181,180,171,175,181,176,177,173,176,184,166,176,183};
public static final int[] g = {145,145,145,145,145,145,145,145,145,145,145,145,146,140,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,127,131,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,144,146,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,122,128,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,144,144,144,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,144,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,144,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,144,145,145,145,145,145,144,144,145,145,145,144,145,144,144,145,145,145,145,145,145,145,142,145,139,145,145,145,145,105,123,140,102,121,146,99,118,144,145,145,145,145,145,145,141,126,138,145,145,145,144,75,113,114,71,110,121,68,99,144,145,145,145,145,145,145,141,155,141,145,145,145,144,75,113,147,72,108,156,68,99,144,145,145,145,145,145,145,142,120,132,145,145,145,145,111,114,119,110,114,120,110,123,145,145,145,145,145,145,145,145,144,144,145,145,145,144,144,144,144,144,144,144,144,144,144,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,133,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,145,144,181,145,145,145,144,145,144,145,145,145,144,145,145,145,145,145,144,145,145,145,145,145,182,134,145,145,145,135,128,142,145,145,145,139,145,145,145,145,137,142,145,145,145,144,122,183,142,145,140,170,127,142,145,145,144,130,145,145,145,145,175,150,145,145,145,144,166,225,160,144,146,181,172,161,143,145,145,126,145,145,145,145,213,186,137,145,145,145,166,224,215,137,167,183,180,181,145,148,149,104,149,148,149,172,224,223,149,150,147,149,169,108,111,106,110,110,101,116,109,97,117,107,101,108,117,106,109,111,106,114,106,107,113,104,106,108,103,106,104,106,107,99,107,111,104,98,116,108,97,112,107,102,108,107,103,99,94,97,100,94,92,104,94,88,108,94,92,105,94,96,99,95,97,97,97,98,93,112,114,112,113,114,108,117,113,105,117,116,109,110,119,113,112,113,112,117,108,112,116};
public static final int[] b = {255,255,255,255,255,255,255,255,255,255,255,254,92,162,254,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,254,21,118,254,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,254,18,117,254,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,254,135,184,254,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,254,254,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,254,253,254,255,255,255,254,254,254,254,254,254,254,254,254,254,255,255,255,255,255,255,238,29,166,255,255,255,252,74,59,33,73,60,32,72,140,254,255,255,255,255,255,255,232,10,152,255,255,255,251,9,7,16,8,6,16,8,101,254,255,255,255,255,255,255,231,8,151,255,255,255,251,10,8,11,8,8,11,8,102,254,255,255,255,255,255,255,247,178,222,255,255,255,254,180,179,179,179,179,179,179,208,254,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,254,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,254,153,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,254,54,254,255,255,254,254,254,254,255,255,254,254,254,255,255,254,254,254,255,255,255,254,54,221,254,255,254,150,71,248,255,255,254,242,254,255,255,254,227,249,255,255,255,255,77,34,209,254,239,17,7,159,254,255,254,54,248,255,255,254,87,155,254,255,255,254,102,1,100,253,135,0,1,41,252,254,250,47,246,254,254,203,12,52,240,254,254,254,103,17,22,214,41,16,14,16,177,243,240,53,230,244,244,114,16,14,173,245,241,245,109,36,39,53,38,39,46,44,40,38,46,34,36,37,54,39,38,53,37,41,52,37,38,26,33,57,25,35,49,30,38,40,32,45,39,23,54,39,23,55,35,25,56,33,25,46,43,43,49,41,37,57,39,31,65,36,33,65,35,35,62,35,36,60,37,38,53,53,51,62,54,51,57,58,52,52,58,57,54,51,63,55,51,61,54,55,59,54,52};

}
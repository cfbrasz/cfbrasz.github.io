import java.awt.*;

public interface GenStage1 {

public static final int nBlocks = 2860;

public static final int blocksPerRow = 44;
public static final double heightWidthRatio = 0.4;
public static final Color bgColor = new Color(0,0,0);

public static final int[] r = {49,58,69,80,89,102,98,100,103,111,126,139,152,160,185,200,209,213,213,215,225,241,245,249,247,239,237,236,237,239,229,218,212,227,237,238,235,231,226,206,161,135,100,74,40,51,62,72,82,91,88,98,108,120,128,147,157,165,192,209,213,217,218,219,229,242,245,250,249,236,237,238,241,242,233,224,222,237,236,236,235,232,223,204,161,122,80,64,40,45,53,68,78,84,92,97,107,123,136,152,159,174,199,216,219,223,226,225,235,245,245,248,240,235,239,241,247,246,241,230,233,242,238,234,237,232,225,209,152,105,90,62,40,46,50,64,76,83,93,100,109,121,143,156,164,179,203,220,225,232,235,232,241,247,246,247,235,234,244,246,247,245,246,233,237,241,241,236,240,235,226,204,154,113,89,69,47,49,54,64,75,80,94,106,118,126,144,162,171,186,203,220,231,241,246,239,243,249,249,247,242,235,249,250,248,249,243,231,241,245,242,238,240,236,220,200,170,124,95,89,49,53,59,68,76,83,96,110,122,135,151,168,178,193,206,224,235,247,249,244,246,250,249,251,249,246,251,249,246,235,227,237,242,246,242,237,240,239,224,200,174,133,107,87,49,58,60,71,75,89,100,115,127,142,158,173,180,198,211,229,240,246,248,250,252,251,251,251,252,252,252,250,240,217,230,243,247,242,242,238,242,240,231,205,169,122,127,96,50,58,61,72,77,91,113,123,133,148,160,173,186,203,216,230,244,249,252,251,253,252,252,251,252,253,252,251,230,220,235,248,249,243,243,242,244,241,234,207,169,132,122,94,49,49,58,72,82,93,115,126,136,149,163,174,192,209,219,232,245,249,253,253,253,252,252,252,252,253,252,247,223,234,246,250,251,240,242,246,247,243,233,203,172,151,107,100,53,47,56,72,88,104,126,136,136,153,167,179,197,215,223,235,245,250,253,253,253,253,253,250,253,253,253,249,233,245,247,252,248,241,241,246,249,244,234,195,176,149,137,119,59,55,50,72,93,106,135,142,140,158,171,183,201,217,226,240,249,252,254,254,253,253,253,251,253,253,253,251,247,246,250,250,239,244,246,248,249,245,238,199,171,155,160,121,61,60,54,66,87,98,133,147,147,158,172,189,209,220,232,244,248,252,254,253,253,253,253,251,253,251,251,244,250,231,240,231,240,248,249,250,248,249,238,205,173,161,154,111,62,60,61,65,87,97,127,149,150,158,172,197,213,222,237,243,249,253,254,253,253,253,253,252,253,246,242,223,233,229,231,241,250,251,251,251,249,248,239,204,178,165,124,100,65,63,62,69,89,96,119,144,154,161,173,201,221,224,238,246,251,253,254,254,254,254,254,253,253,232,221,226,226,231,247,251,253,253,252,252,252,249,234,211,183,140,101,95,67,66,69,72,92,96,116,138,156,162,179,204,223,228,242,248,253,253,254,254,254,254,254,250,240,231,229,241,232,241,250,253,253,253,253,253,252,244,223,197,143,96,95,95,73,74,73,82,97,97,115,137,157,165,185,206,226,232,247,250,253,253,254,253,254,253,254,235,233,248,232,234,240,248,253,253,253,253,253,253,251,237,200,135,99,93,97,94,81,81,85,89,105,108,119,140,156,172,190,209,228,237,249,252,253,254,254,254,254,254,253,237,242,247,231,243,251,253,253,253,253,253,254,253,237,217,161,124,95,94,97,96,83,90,94,102,112,121,129,144,155,181,198,213,229,242,251,253,254,254,254,253,254,253,253,239,236,235,246,252,252,254,253,254,253,253,253,250,209,172,170,125,97,97,99,96,86,99,107,118,123,137,144,150,159,177,196,215,226,245,252,253,253,253,253,254,254,253,254,236,235,246,252,253,254,253,253,253,254,252,238,228,178,170,157,111,102,97,104,97,94,107,116,127,127,140,154,157,164,182,199,216,225,246,253,254,253,253,253,253,254,254,252,234,242,251,253,253,253,253,253,253,248,235,208,193,164,144,119,114,109,102,106,98,107,117,120,133,136,135,167,177,181,197,212,213,229,249,253,254,253,254,254,254,254,254,247,244,249,253,253,253,253,253,253,243,235,234,215,198,175,147,125,120,117,109,107,103,113,125,128,137,141,139,166,195,202,211,225,224,235,249,254,254,254,254,254,254,254,253,240,244,252,253,254,253,254,253,242,219,219,210,208,193,164,132,121,125,124,114,108,106,118,123,141,140,143,139,160,199,215,219,235,236,239,253,254,254,254,253,254,254,253,250,237,249,253,253,254,254,253,240,239,219,174,160,152,141,137,136,132,128,127,116,111,107,125,132,153,159,151,151,173,197,215,222,236,238,244,254,253,254,254,254,253,254,253,249,247,252,253,253,253,253,238,217,241,240,169,145,143,138,144,144,138,135,128,117,116,109,141,146,158,169,171,176,187,201,205,217,231,240,248,253,254,254,254,254,253,253,253,251,250,254,253,254,252,239,212,228,244,221,146,145,146,141,139,143,143,139,136,118,117,114,155,160,173,179,189,204,207,205,211,223,231,242,251,253,254,253,254,253,254,253,253,252,253,254,253,253,228,218,216,235,224,188,152,150,152,148,147,148,149,145,142,135,123,117,165,177,191,198,206,214,216,221,219,235,240,246,253,254,253,254,254,254,253,253,253,253,253,253,254,247,222,221,224,215,182,159,157,155,153,153,150,152,154,151,146,142,134,120,187,200,212,213,215,220,227,225,221,234,243,250,253,253,254,253,254,254,254,253,253,254,253,254,254,229,209,233,215,176,163,158,167,166,163,160,161,154,154,158,151,152,147,128,203,221,222,224,225,221,227,232,235,233,245,252,253,253,253,253,254,253,254,254,253,254,253,253,253,213,195,191,175,170,168,165,173,176,177,172,171,166,158,158,159,158,154,140,216,228,228,225,231,231,233,234,241,242,246,253,253,253,253,254,253,253,254,254,254,254,253,250,248,194,195,187,173,176,175,174,175,179,185,180,176,179,170,167,171,163,160,155,200,226,230,230,227,233,234,242,241,242,240,252,253,254,253,254,254,254,254,254,254,254,254,251,236,193,188,182,181,178,184,183,183,185,191,185,178,181,177,176,181,174,165,163,187,222,228,227,227,231,237,246,242,234,235,247,253,253,254,254,254,254,254,254,254,254,254,254,235,198,186,177,191,195,191,192,197,197,195,190,186,186,185,188,188,179,172,170,181,220,230,230,233,234,243,245,237,226,233,247,253,254,254,254,254,254,254,254,254,254,254,254,221,201,185,189,205,204,199,200,201,203,202,199,195,192,192,193,193,184,176,175,195,219,235,230,239,243,246,239,228,221,230,241,253,253,254,254,254,254,254,254,254,254,254,254,217,203,201,201,205,206,208,207,203,206,206,205,204,201,198,198,196,197,180,176,219,223,233,236,240,245,248,237,223,221,229,238,253,253,254,254,254,254,254,254,254,254,254,254,228,214,214,209,210,209,219,217,212,216,212,207,210,209,205,204,203,199,191,185,230,229,230,235,245,247,246,226,214,221,233,242,253,253,253,254,254,254,254,254,254,254,254,251,222,224,225,220,222,222,223,220,221,224,218,216,214,218,214,214,208,207,203,194,226,232,234,240,248,243,241,221,230,229,241,247,253,254,254,254,254,254,254,254,254,254,254,237,221,230,232,229,232,230,225,227,227,230,229,224,221,222,218,220,216,213,211,205,219,234,244,247,238,229,244,244,245,249,251,252,253,253,254,254,254,254,254,253,253,254,254,242,236,235,239,244,239,239,235,233,233,233,233,232,229,227,224,224,223,220,216,211,243,247,250,249,219,224,233,244,246,252,254,253,253,253,253,253,253,253,253,253,253,253,253,251,246,251,252,252,246,246,242,242,241,239,240,239,237,237,232,228,225,225,220,217,246,251,251,251,223,201,214,229,237,248,253,254,253,254,253,253,253,253,253,253,253,253,253,253,253,253,252,251,248,247,248,248,245,245,246,244,242,242,239,235,232,232,228,223,250,251,249,245,236,224,224,218,213,233,245,253,253,253,253,253,253,253,253,253,253,253,253,253,253,253,250,253,254,252,249,250,250,248,246,246,244,241,239,241,235,234,233,230,244,243,241,236,244,239,236,228,221,235,251,253,253,252,253,253,253,253,253,253,253,253,253,253,251,253,252,254,254,252,250,249,253,252,249,246,244,242,240,240,236,235,234,233,240,238,228,241,247,239,244,246,237,243,252,252,253,253,253,253,253,254,253,254,254,253,253,253,249,249,252,253,252,248,248,250,252,249,242,243,246,238,229,232,234,232,230,232,228,214,208,228,230,220,226,222,218,229,236,247,254,253,253,253,253,253,253,254,253,253,253,253,242,243,250,252,250,250,249,250,251,245,238,247,247,242,240,242,236,226,227,231,213,189,180,184,193,188,189,194,207,218,225,237,250,253,253,253,253,253,254,253,252,253,253,253,253,253,253,253,253,248,245,247,251,251,250,251,249,245,247,241,242,237,234,233,207,185,175,170,176,184,192,194,205,210,216,228,246,251,253,253,253,253,253,253,252,253,253,253,252,251,252,251,249,250,251,250,253,253,253,252,249,234,247,244,248,243,236,237,208,183,166,174,180,192,195,196,202,206,206,213,234,246,252,253,253,253,253,253,252,253,253,252,239,230,225,224,228,227,233,235,245,252,252,252,250,242,248,246,246,243,239,240,210,186,167,177,186,191,190,193,196,196,202,207,219,236,245,248,252,243,242,248,244,238,242,216,205,207,208,215,215,218,216,220,238,251,253,252,253,253,251,250,249,247,241,244,186,174,169,168,172,181,185,187,192,193,192,203,213,224,232,235,235,222,224,233,232,240,225,238,237,232,228,237,232,223,224,228,252,253,253,253,253,253,253,251,249,246,240,238,181,171,158,157,174,178,182,186,189,189,188,196,203,209,216,223,219,207,206,206,216,233,223,224,222,218,212,212,218,206,213,217,236,253,253,252,252,252,249,247,246,242,230,228,174,167,155,161,167,172,178,180,183,185,188,193,197,202,209,212,211,204,200,206,215,217,215,217,223,227,229,222,232,240,244,247,251,253,253,251,247,243,244,241,237,230,216,214,165,162,158,156,162,167,175,180,181,180,184,188,192,200,207,205,204,198,206,213,217,225,234,238,240,240,233,238,232,232,234,241,243,245,240,232,227,228,224,216,214,207,200,199,162,158,147,146,160,166,165,166,173,172,171,171,177,179,187,187,188,187,187,186,197,208,213,220,222,214,209,211,207,198,195,198,198,199,196,188,176,183,188,182,185,185,183,172,145,145,143,144,150,156,151,156,159,157,160,166,160,158,159,162,163,167,164,157,163,166,172,170,175,164,168,156,141,133,133,127,138,144,122,115,98,101,89,93,93,106,96,89,104,103,103,106,99,101,104,100,93,107,104,119,107,98,106,109,122,115,92,87,85,88,74,76,81,89,98,88,83,80,68,63,58,79,60,48,50,55,53,54,52,54,48,61,14,16,17,12,11,9,16,26,33,35,38,47,49,48,59,52,61,61,65,52,61,57,57,56,57,61,68,61,65,75,61,48,53,48,48,42,43,36,38,30,38,42,38,35,1,2,1,1,1,2,3,7,20,19,22,19,27,17,29,31,29,30,41,40,51,56,51,47,58,45,50,50,48,54,58,35,44,39,41,32,43,41,38,36,46,39,21,10,1,1,3,1,5,4,6,5,3,5,20,22,26,23,27,24,38,31,34,38,47,53,46,43,47,43,45,28,27,35,54,43,46,41,38,33,25,21,29,33,35,45,33,29,2,0,3,0,0,0,0,0,2,1,6,7,14,13,24,24,29,39,29,33,41,49,32,43,45,52,50,40,45,56,57,59,58,58,59,66,57,65,80,98,98,108,115,154,2,4,5,12,15,12,12,15,8,13,18,16,25,20,27,31,37,49,50,48,60,66,68,69,61,69,76,82,85,83,58,66,97,106,108,116,157,167,166,201,220,219,201,215,39,43,68,62,43,26,5,7,10,10,16,25,29,36,35,58,87,91,94,104,138,139,135,188,197,203,206,215,218,222,221,222,226,225,229,232,237,239,233,237,238,241,226,230,178,190,193,198,191,166,140,137,144,155,159,165,184,194,199,223,239,243,250,252,253,252,251,244,241,236,237,245,247,245,246,246,246,245,242,240,236,237,239,244,244,240,233,243,170,182,189,201,199,203,200,197,201,202,209,216,231,242,249,253,253,253,253,253,253,253,253,253,253,250,246,246,247,248,249,248,250,249,247,247,242,242,242,241,245,243,237,243,153,174,182,184,189,193,199,200,206,212,219,234,251,253,253,253,253,253,253,253,253,253,253,253,253,250,241,248,247,247,242,242,245,242,246,247,243,243,242,238,240,244,242,242,150,160,180,181,179,184,192,198,201,214,220,237,248,253,253,253,253,253,253,253,253,253,253,253,252,246,241,238,239,241,240,236,238,241,244,246,242,245,248,245,241,245,244,240};
public static final int[] g = {25,30,36,44,52,64,67,71,73,73,79,85,90,92,105,116,124,140,151,155,166,176,197,185,175,159,177,162,162,189,177,172,167,190,205,209,211,209,187,170,151,141,141,141,19,26,33,35,45,57,59,68,74,74,79,86,90,94,102,119,126,148,159,166,178,193,213,187,173,147,179,166,172,200,193,178,181,202,193,201,212,207,185,169,154,144,152,149,15,23,29,34,43,51,56,65,72,74,81,91,92,97,110,126,140,163,175,177,188,206,212,194,175,155,177,179,181,202,204,182,196,206,188,192,214,206,184,164,154,147,155,150,14,23,27,32,38,48,55,67,72,75,85,94,95,101,119,133,152,176,189,187,196,209,216,199,183,175,182,190,198,208,208,175,201,194,189,195,213,207,180,163,157,154,154,154,18,23,26,33,42,46,55,68,74,77,85,95,100,106,125,146,163,180,194,195,200,212,219,206,186,186,198,206,207,217,201,174,202,190,179,199,212,207,179,175,165,156,157,159,21,26,27,36,45,48,57,70,75,80,88,100,105,110,129,153,173,186,193,201,205,217,226,217,204,195,212,213,203,186,165,187,203,187,177,197,218,209,184,183,165,153,159,160,21,27,26,35,44,53,65,71,78,82,92,101,104,113,134,155,177,191,196,204,207,222,232,225,214,207,217,215,179,143,166,198,205,184,182,203,219,211,193,182,163,153,161,165,23,27,26,33,46,60,71,76,78,82,93,100,105,117,138,154,180,192,201,206,212,223,230,221,215,216,214,214,170,147,177,210,206,180,185,208,220,212,200,177,161,157,164,168,23,26,25,35,51,61,76,80,79,85,95,101,109,121,139,159,181,191,204,210,218,223,226,204,218,214,214,201,152,173,192,222,206,178,193,217,222,218,209,183,169,163,165,165,26,24,27,42,56,66,81,82,83,89,98,104,111,125,141,167,187,198,211,214,221,224,221,187,211,215,218,203,166,197,213,224,205,194,206,223,221,220,215,188,176,161,159,160,29,26,28,44,65,72,82,85,84,92,100,105,112,126,143,172,190,202,219,218,222,224,220,179,204,214,213,209,196,201,221,210,185,209,226,228,220,218,212,192,174,161,159,162,30,27,26,39,57,68,82,87,90,92,97,108,115,126,143,174,192,205,219,220,222,222,218,179,208,209,212,189,204,162,182,165,183,220,227,225,215,217,208,195,173,165,158,176,31,29,26,38,54,64,80,84,90,94,97,112,125,128,147,166,190,206,219,220,225,223,220,194,218,201,198,155,185,157,156,174,211,225,224,221,212,220,211,196,180,166,166,185,35,30,30,35,52,60,78,87,95,99,98,114,132,133,153,171,189,209,222,221,226,223,221,210,219,176,160,158,158,157,182,207,226,228,226,216,220,223,208,193,175,169,182,188,41,37,39,41,54,60,75,87,95,102,105,118,139,139,158,182,191,212,223,224,227,227,219,204,196,166,154,189,164,173,203,223,224,225,226,222,229,218,203,186,178,184,187,189,51,46,47,52,57,59,73,84,95,102,113,121,140,143,163,192,196,208,222,226,228,226,221,178,170,198,162,177,186,203,219,229,226,225,226,233,228,212,204,195,190,192,191,189,57,54,57,61,63,68,75,85,96,104,116,127,140,148,167,194,203,203,221,228,232,227,229,177,184,201,176,185,208,218,230,225,222,230,235,233,218,210,198,196,195,194,194,189,61,67,70,74,70,74,81,87,96,109,117,133,143,154,169,192,212,210,222,231,233,229,227,176,186,173,195,211,224,235,231,223,228,235,236,220,199,196,183,197,199,195,194,191,65,75,77,81,75,78,86,88,96,107,118,134,150,156,174,197,212,221,228,237,237,233,225,173,170,187,211,222,234,237,236,230,233,227,212,203,201,192,194,204,201,198,196,192,72,79,83,84,82,79,86,89,98,111,123,136,163,169,187,204,212,225,235,243,239,236,215,166,184,212,222,227,236,236,240,234,223,208,206,193,190,196,204,208,203,201,197,194,78,84,86,90,90,87,88,99,106,119,133,137,169,194,198,214,219,232,239,241,240,237,193,171,199,219,231,224,236,239,234,219,206,208,205,188,182,188,201,210,208,202,200,197,81,90,91,95,97,93,92,108,115,127,141,149,175,209,213,219,226,235,239,235,236,233,175,174,210,219,240,233,240,236,211,200,208,203,192,192,196,201,210,214,211,205,201,199,81,86,96,96,98,95,92,106,119,129,143,152,178,215,221,223,234,239,241,231,221,203,167,189,229,234,243,244,232,217,208,203,213,213,205,203,210,220,216,215,213,207,203,200,84,90,94,100,100,97,98,100,113,134,147,157,191,220,224,219,234,236,235,215,187,178,178,205,236,244,243,238,223,214,210,214,217,225,222,222,227,225,220,217,213,208,206,202,91,95,93,99,103,107,101,96,116,159,169,180,196,216,216,214,229,232,217,197,183,178,185,220,241,245,240,226,231,210,214,212,226,230,231,228,226,226,224,219,216,209,207,204,98,95,95,98,104,120,115,107,137,170,169,171,185,204,207,206,222,220,209,194,205,197,213,234,248,249,231,237,229,215,208,216,231,235,235,231,231,229,226,224,220,213,209,206,98,96,100,104,109,122,130,134,154,172,176,169,177,192,209,208,220,218,198,200,219,215,229,244,249,246,224,234,222,217,219,230,237,237,235,235,233,231,230,227,223,218,212,208,99,103,118,109,111,125,138,146,159,169,176,176,168,182,206,212,220,217,203,216,225,230,238,248,250,233,234,225,218,228,234,241,244,241,240,241,236,233,230,230,225,222,218,210,109,119,127,125,123,132,161,168,168,170,173,182,174,182,207,225,228,223,227,235,234,242,242,241,245,229,247,239,237,243,244,243,246,245,245,241,241,238,234,229,229,225,221,216,127,140,137,130,137,143,172,181,178,169,169,178,186,199,221,237,237,237,240,245,250,247,247,239,231,234,248,246,244,248,247,246,245,245,248,245,243,242,237,234,234,228,225,222,103,132,145,143,141,154,168,181,178,158,154,170,191,210,226,241,246,246,248,253,253,250,252,246,234,240,246,246,249,249,250,249,249,248,249,247,243,242,240,237,237,231,227,224,104,128,142,157,158,163,169,172,177,140,143,159,190,211,232,242,249,251,252,254,254,254,254,254,240,246,246,244,252,252,251,251,251,251,251,249,247,245,243,241,240,234,230,226,110,116,139,163,171,173,171,160,146,132,143,162,187,210,232,243,250,254,254,254,254,254,254,254,240,250,247,246,254,253,253,252,252,252,252,251,249,247,245,243,241,237,231,229,114,127,143,159,175,181,174,141,128,131,151,164,188,215,235,247,253,254,254,254,254,254,254,251,238,251,250,252,253,254,253,253,252,253,252,252,251,249,246,246,242,241,234,231,143,148,153,163,174,176,162,130,129,131,152,165,191,216,236,249,254,254,254,254,254,254,254,251,240,252,253,254,254,254,252,254,253,253,253,252,252,251,249,247,244,242,236,232,160,163,172,179,174,164,145,126,130,131,155,167,197,215,235,247,254,254,254,254,254,254,254,241,246,252,253,254,254,254,253,254,254,254,254,253,252,252,251,247,245,243,239,237,161,169,178,185,171,157,164,139,160,147,160,171,202,224,238,247,254,254,254,254,253,254,254,235,249,253,253,254,254,254,254,254,254,254,254,254,254,253,251,251,247,244,240,237,160,167,177,171,154,155,180,175,172,177,170,180,207,228,238,249,253,254,249,237,237,246,244,236,247,252,253,252,254,254,254,254,254,254,254,254,254,254,253,252,250,246,242,240,169,175,169,148,130,155,159,175,166,183,179,194,210,220,230,230,227,223,214,207,205,231,233,229,235,233,227,233,245,245,254,254,254,254,254,254,254,254,253,252,250,249,245,241,152,158,160,149,125,112,132,146,159,171,178,190,196,204,208,209,207,208,199,203,211,224,229,225,229,216,224,253,253,253,254,254,254,254,254,254,254,254,254,253,252,251,250,245,152,155,147,142,146,135,144,126,120,142,152,175,175,176,190,199,206,214,210,197,212,205,201,209,232,237,253,254,254,254,254,254,254,254,254,254,254,254,253,253,252,251,251,249,154,133,138,145,157,157,156,141,131,143,159,173,160,137,159,194,206,212,214,204,200,208,218,234,251,253,254,254,254,254,254,254,254,253,253,253,253,252,245,243,247,248,247,247,135,142,137,152,157,150,158,162,158,161,166,165,160,158,193,204,212,222,228,226,223,232,213,224,221,219,233,245,244,233,240,250,245,198,220,206,220,229,180,179,229,237,236,240,120,126,133,145,146,139,145,151,148,150,152,154,165,170,195,205,216,223,227,221,217,207,208,222,184,197,189,215,223,218,230,238,232,197,192,185,211,222,192,206,219,187,215,231,110,111,107,106,114,110,121,127,140,146,148,152,157,161,185,203,212,219,216,199,191,197,207,219,210,216,201,217,214,207,223,231,238,222,232,228,233,227,200,223,193,178,211,212,110,103,105,108,111,125,137,133,145,152,152,154,154,153,165,190,199,200,197,187,183,188,199,192,184,188,190,183,183,167,184,213,232,235,237,236,229,192,175,216,194,195,212,207,112,109,107,117,126,138,142,138,148,151,149,152,152,158,158,159,184,185,175,186,179,169,180,174,161,157,157,162,160,173,165,171,200,218,215,215,219,195,189,211,209,205,202,203,121,112,111,123,136,139,140,139,142,142,146,149,154,156,161,149,173,179,176,184,165,155,157,144,145,146,148,160,161,163,161,158,185,212,213,208,213,208,205,201,200,199,194,192,110,105,116,118,123,131,133,135,135,136,140,146,154,160,160,162,163,165,160,158,160,164,151,162,162,158,159,167,168,164,163,164,199,204,201,200,202,199,194,188,188,188,178,173,108,106,107,112,125,128,131,133,136,135,137,143,148,153,160,162,160,150,145,149,156,168,158,159,157,163,160,155,163,155,159,163,184,202,198,192,192,187,181,182,178,173,167,162,109,110,109,115,119,123,127,130,134,138,142,144,148,154,159,161,159,151,145,156,163,162,163,164,171,178,177,166,181,191,193,190,194,191,190,187,186,181,176,173,172,166,163,159,113,111,112,112,113,116,127,131,133,136,138,143,152,159,163,164,164,157,161,170,173,173,183,183,186,188,179,190,186,187,185,186,183,185,180,175,173,177,171,164,162,158,154,151,115,111,107,108,114,119,124,127,130,130,132,135,139,143,150,150,153,152,151,150,164,174,179,180,181,171,170,175,171,163,159,160,154,154,155,149,143,147,150,145,146,147,146,135,106,103,103,106,112,119,119,119,122,125,127,134,136,136,135,137,140,143,141,135,138,142,145,146,151,140,142,137,124,120,121,120,126,127,111,106,95,94,86,90,86,94,88,81,90,87,88,95,97,97,98,94,89,100,96,107,104,99,105,109,118,112,97,93,88,94,85,86,88,94,96,89,89,89,81,83,74,89,76,66,65,64,60,61,61,57,50,63,28,26,29,29,28,27,35,42,49,48,47,58,62,64,68,64,74,76,77,69,68,66,72,70,65,72,76,65,76,87,78,70,62,66,67,59,55,38,43,41,44,40,37,39,9,13,8,14,7,13,15,20,39,28,32,32,41,33,38,40,37,36,51,49,53,64,59,55,66,60,62,61,63,69,72,53,54,53,53,48,56,47,42,42,50,41,23,13,10,3,12,7,15,9,14,13,11,12,33,34,33,36,41,35,47,40,41,44,52,63,53,50,57,50,57,43,39,49,66,61,55,52,46,41,32,28,31,34,33,43,39,37,6,0,9,1,0,0,0,1,6,3,11,12,22,21,31,31,34,41,35,36,45,54,44,52,52,54,54,47,52,62,63,66,62,60,61,70,62,65,75,88,91,107,128,166,8,8,7,16,15,16,15,18,13,18,25,22,32,27,35,40,46,58,60,58,69,75,79,75,68,70,75,79,79,75,56,60,81,84,85,97,132,146,157,194,218,222,208,226,32,33,49,55,46,31,10,11,15,20,24,38,40,45,46,66,89,93,94,100,123,120,120,160,164,163,161,161,164,172,177,178,181,190,200,207,223,232,229,235,236,245,234,243,86,98,98,112,125,118,105,107,113,118,123,131,140,143,148,169,180,180,187,195,205,195,191,184,177,171,173,181,188,193,191,184,189,196,200,203,208,210,222,240,244,242,235,246,85,96,95,112,114,121,122,132,139,142,145,145,154,164,171,186,192,187,191,201,207,201,196,188,183,183,178,187,188,190,188,183,188,195,198,204,205,208,214,227,243,246,239,248,68,84,101,95,113,115,126,127,135,137,141,141,148,169,180,186,196,190,186,194,203,198,199,196,187,184,165,185,180,182,173,171,178,181,193,203,204,206,215,218,228,246,247,253,69,73,96,89,97,98,116,120,122,132,133,141,146,164,172,185,200,195,193,193,206,203,203,190,186,169,166,167,166,170,173,174,178,182,193,206,213,217,226,227,233,247,247,250};
public static final int[] b = {46,51,50,41,39,47,65,82,83,81,67,64,67,57,53,59,66,81,88,82,84,90,110,105,94,83,114,101,93,104,99,109,116,139,131,147,145,124,111,140,144,141,158,171,46,54,53,43,42,51,68,81,81,73,64,62,63,56,48,57,68,84,90,86,92,106,128,110,97,85,113,107,86,114,126,121,120,135,131,137,147,123,107,139,154,143,186,188,45,51,53,50,49,52,67,74,74,64,57,61,60,52,52,62,73,92,98,97,100,119,122,109,111,95,113,96,91,123,134,117,124,139,118,128,141,117,110,133,149,153,174,191,42,46,52,52,55,58,65,72,69,64,60,62,59,53,57,65,75,97,107,105,105,121,125,109,119,122,111,93,103,137,137,107,119,119,112,124,134,112,122,140,156,156,173,190,43,49,46,48,53,59,67,72,61,64,58,62,58,55,64,75,81,95,110,118,111,125,130,115,108,125,127,121,126,132,117,104,119,106,109,122,129,114,136,157,159,156,177,187,46,51,48,52,57,59,66,68,59,60,55,63,62,56,67,79,89,90,103,119,121,131,138,124,113,119,134,136,133,121,110,110,115,107,105,112,125,116,140,163,159,155,176,192,48,53,49,53,57,63,70,64,59,56,56,59,58,57,71,80,94,93,95,121,130,137,148,139,121,119,136,140,116,111,130,118,121,107,110,112,128,124,146,155,156,157,169,189,49,53,48,54,57,65,73,66,60,50,56,54,56,57,73,82,96,98,93,116,135,137,144,140,130,119,134,135,109,112,127,119,121,108,106,113,124,126,145,142,156,162,175,192,48,49,49,58,62,66,70,68,60,52,55,51,56,61,75,88,101,102,109,120,140,132,136,124,141,119,139,135,110,113,112,120,120,103,107,122,123,124,131,148,167,163,179,184,47,50,53,62,68,69,77,66,63,54,59,49,51,61,77,95,106,107,125,126,139,133,130,115,129,125,142,137,122,109,110,126,103,103,117,123,125,119,129,161,172,161,161,169,52,49,52,64,75,77,68,67,62,59,57,54,50,63,77,100,109,117,137,134,137,134,131,119,121,126,133,130,119,106,121,120,99,110,133,125,112,107,104,159,169,162,154,173,52,48,49,62,72,75,73,69,69,62,55,61,55,64,78,98,109,121,135,136,137,135,132,120,124,124,120,113,79,115,117,114,108,120,123,118,117,115,108,158,169,163,155,205,54,52,48,61,71,71,75,63,69,65,55,64,64,65,78,92,107,122,137,134,142,135,123,121,128,115,118,117,105,118,111,115,118,115,115,116,130,120,119,161,180,161,183,229,60,54,52,57,66,72,75,73,77,71,57,64,74,70,80,97,108,125,140,140,144,132,109,116,115,118,126,124,122,112,113,124,113,117,119,123,132,122,132,166,170,178,218,234,67,60,61,63,64,72,73,78,80,73,66,63,79,75,83,104,106,127,141,143,146,118,124,109,103,123,114,129,118,113,121,130,117,119,119,128,121,125,141,175,196,227,234,235,78,68,67,72,65,70,74,73,82,73,68,64,80,80,86,108,109,122,138,145,138,115,128,113,123,122,120,109,115,119,123,132,130,123,120,123,127,130,179,220,233,242,239,236,81,78,79,77,69,73,75,72,80,78,71,68,76,88,90,109,119,118,139,145,118,113,125,114,129,127,125,112,117,116,121,129,130,128,126,129,135,161,206,229,247,245,241,237,83,84,84,84,73,72,76,71,80,85,75,73,74,89,89,111,128,130,141,148,127,109,122,114,123,120,117,123,123,123,131,132,130,132,141,136,178,201,187,232,250,246,242,239,87,92,92,89,76,61,66,65,73,80,81,72,72,89,94,116,132,146,151,158,125,109,122,116,126,117,131,123,131,128,143,143,142,142,140,155,202,195,208,250,250,248,244,240,91,95,97,91,81,64,58,59,64,71,83,74,82,89,103,127,138,149,160,163,115,116,115,113,127,134,129,114,143,146,149,149,152,163,198,192,195,212,243,252,251,248,246,242,97,102,97,92,90,82,50,57,64,71,80,77,88,110,115,136,138,155,162,161,115,127,116,99,126,139,133,108,144,160,154,151,179,193,201,187,189,199,232,252,251,249,246,244,97,98,97,92,92,93,54,50,58,68,81,85,93,124,127,134,139,161,160,156,125,138,123,112,125,137,147,137,149,166,166,177,197,201,190,192,207,229,250,253,252,250,248,245,87,89,81,85,90,92,58,40,47,61,77,83,99,120,133,138,143,162,161,146,126,133,131,127,137,147,148,148,164,187,193,196,227,239,235,232,245,252,252,253,253,251,249,246,81,80,64,67,85,82,56,34,40,59,71,82,106,123,137,149,150,160,153,130,111,122,133,139,148,151,142,176,207,215,195,195,237,253,253,253,253,253,253,253,252,251,250,248,70,65,52,53,65,72,48,31,44,68,75,86,101,122,136,146,152,154,139,126,109,121,130,152,156,147,170,206,241,206,195,206,252,253,253,253,253,253,253,253,252,252,249,249,63,54,46,47,50,65,55,47,64,82,87,85,88,115,133,130,146,147,135,112,121,135,149,161,158,170,212,246,237,203,201,227,253,253,253,253,253,253,253,253,253,252,251,249,55,49,48,48,50,60,65,74,83,88,95,97,94,102,141,137,146,142,119,115,145,145,152,164,158,191,223,238,217,214,239,250,253,253,253,253,253,253,253,253,253,252,252,250,50,50,62,49,54,65,72,81,89,93,89,102,96,97,129,142,144,130,118,128,148,154,155,165,162,202,243,221,219,248,253,253,253,253,253,253,253,253,253,253,253,253,252,251,59,60,69,67,64,71,80,86,91,96,96,100,96,98,124,153,145,142,142,153,153,158,163,163,177,216,253,251,252,253,253,253,253,253,253,253,253,253,253,253,253,252,252,251,74,84,80,74,79,69,79,90,90,93,103,101,101,107,151,167,158,158,153,160,160,163,163,177,179,241,253,253,253,253,253,253,253,253,253,253,253,253,253,253,253,253,252,251,58,74,83,85,82,80,81,93,96,95,100,98,102,115,145,173,180,172,168,173,167,169,165,179,189,251,253,254,253,253,253,253,253,253,253,253,253,253,253,253,253,252,252,252,68,76,83,79,80,83,86,88,99,94,101,96,98,111,133,169,188,184,175,175,172,172,173,171,210,253,253,253,253,253,253,253,253,253,253,253,253,253,253,253,253,252,252,252,88,64,72,78,80,88,87,86,85,95,101,100,97,109,137,169,182,187,178,172,177,176,177,173,225,253,253,253,254,254,254,253,253,254,253,253,253,253,253,253,253,252,252,251,73,66,69,77,85,93,90,84,90,96,106,108,101,104,135,168,183,190,183,180,180,177,183,180,225,253,253,253,254,254,254,253,253,253,253,253,253,253,253,253,253,253,252,251,78,77,79,84,85,85,79,79,96,99,108,111,108,101,130,175,183,185,183,181,177,171,179,188,217,253,254,254,254,254,253,254,253,254,254,253,254,253,253,253,253,253,253,252,77,82,84,88,83,79,75,89,107,105,107,115,113,112,129,166,184,187,186,189,183,163,168,176,242,253,254,254,254,254,254,254,254,254,254,254,253,253,253,253,253,253,251,250,84,76,89,90,81,78,93,105,122,108,106,118,114,130,148,161,174,189,196,193,188,171,170,201,253,253,254,254,254,254,254,254,254,254,254,254,254,254,253,253,253,252,251,248,92,79,80,81,78,88,101,107,117,111,105,118,116,128,150,167,183,189,192,180,173,170,165,210,245,250,251,235,254,254,254,254,254,254,254,254,254,254,254,253,253,252,251,248,86,83,84,74,83,95,95,101,107,104,99,114,109,128,153,162,167,164,156,145,139,146,159,179,216,182,161,193,237,235,254,254,254,254,254,254,254,254,254,253,253,253,251,248,72,76,78,73,90,92,101,96,102,98,90,104,111,134,150,155,152,153,143,139,130,118,134,135,164,156,183,248,254,253,254,254,254,254,254,254,254,254,254,254,253,253,252,251,72,76,74,84,97,107,113,97,99,101,108,110,116,125,139,146,153,156,152,140,146,133,128,135,189,213,250,254,254,254,254,254,254,254,254,254,254,254,253,253,253,252,252,251,74,67,75,99,99,116,114,107,107,114,115,114,107,109,123,137,146,146,133,135,139,150,155,183,234,251,254,254,254,254,254,254,254,253,253,252,253,253,245,239,246,249,249,249,73,72,81,92,94,102,102,101,109,121,120,118,104,108,144,147,140,139,138,145,164,176,154,190,204,207,220,237,238,229,237,249,242,197,219,204,213,229,183,176,225,232,231,236,71,86,100,93,102,107,100,116,119,122,119,124,113,121,144,143,130,129,137,146,162,153,161,174,179,174,174,200,218,213,226,232,225,198,200,187,209,220,193,203,214,183,205,222,74,93,95,100,103,102,113,118,125,125,125,129,132,129,140,149,133,114,126,135,151,146,145,156,163,167,162,167,175,191,214,222,228,212,226,222,227,222,186,212,183,169,195,188,72,81,93,108,111,120,132,128,132,135,131,134,144,132,125,149,155,138,138,136,135,137,142,132,148,150,156,160,162,157,166,193,209,205,207,215,205,191,165,201,178,179,191,176,76,84,95,108,118,130,132,129,135,135,132,132,147,150,133,115,148,151,142,145,141,115,130,133,154,159,165,174,172,192,181,179,179,174,172,178,185,181,166,177,173,169,161,162,78,87,102,112,128,131,132,130,130,129,134,132,137,150,152,126,147,158,156,163,147,144,146,160,167,171,171,178,175,181,183,173,172,177,172,166,167,163,159,153,146,142,141,141,97,99,114,115,117,123,127,126,127,129,131,133,134,144,153,148,146,151,156,167,167,161,154,157,164,167,169,176,173,181,185,175,162,160,163,159,157,150,148,145,137,134,132,130,107,103,107,114,123,124,126,126,131,132,132,134,137,145,152,153,149,143,151,171,164,165,166,171,173,184,182,174,178,178,175,172,179,162,157,156,154,150,150,152,140,140,139,130,106,107,111,118,121,120,123,128,129,134,138,142,142,150,152,154,153,148,148,155,163,163,168,170,166,180,179,175,169,165,163,162,159,158,153,152,151,148,143,143,144,136,136,132,114,113,114,114,114,114,123,130,133,134,137,141,149,156,160,157,158,154,153,156,157,161,165,167,162,162,161,164,161,161,162,157,157,151,151,148,144,146,146,143,137,132,130,129,113,112,108,113,116,119,125,129,135,136,138,138,140,143,150,148,153,148,147,150,161,168,164,164,162,167,155,159,162,167,164,165,165,158,153,145,133,134,136,133,134,134,131,125,113,118,114,120,122,126,124,127,133,133,135,144,138,138,140,142,142,142,141,139,146,150,158,152,166,152,150,142,124,120,117,116,122,121,105,99,87,88,80,81,81,86,78,70,92,88,88,96,95,94,91,88,81,93,93,107,103,93,99,103,114,109,92,85,85,89,84,80,77,84,93,83,82,84,78,77,72,80,74,70,66,60,51,56,55,51,43,49,15,18,15,16,18,18,23,30,38,35,37,46,51,54,59,58,65,68,71,64,65,60,69,67,59,66,71,63,70,81,75,70,62,63,68,58,49,33,39,44,40,37,33,36,4,10,7,12,8,11,13,16,29,21,27,30,40,32,35,38,32,30,41,45,49,59,52,52,59,56,60,62,65,71,69,57,55,55,53,49,49,39,40,43,46,39,25,17,6,2,9,8,12,8,13,12,11,9,25,27,29,31,35,30,39,31,30,36,44,54,45,46,55,47,57,49,48,52,64,60,48,46,43,36,30,27,26,32,32,40,36,38,4,0,5,1,0,0,0,2,8,7,11,8,21,22,29,27,31,34,31,33,40,48,44,55,54,51,50,47,48,56,50,57,50,49,48,55,51,56,64,79,88,108,137,170,3,3,4,10,9,10,10,11,10,17,22,25,33,32,35,37,44,50,53,53,62,67,71,70,68,68,71,77,78,74,58,61,74,79,75,95,128,145,157,190,216,223,209,225,23,21,32,32,24,19,5,8,15,21,27,38,43,46,46,54,62,58,58,62,72,62,65,97,124,130,130,132,131,141,151,152,156,171,188,202,221,230,225,232,234,243,234,240,57,61,60,71,80,80,73,79,86,89,96,102,105,91,66,71,72,71,87,100,119,99,108,127,136,137,140,149,157,168,164,158,170,181,186,193,205,210,219,239,243,241,235,243,63,66,61,69,76,78,84,95,102,108,101,90,83,84,93,98,90,91,111,119,125,130,130,107,91,137,151,161,162,170,163,157,160,178,183,188,194,202,211,224,239,246,239,248,56,64,73,61,80,80,90,97,104,101,98,89,84,95,103,106,110,112,110,112,118,129,131,121,132,147,137,155,153,154,148,149,155,160,174,188,196,201,211,215,224,246,248,252,57,59,69,59,69,72,87,89,97,97,97,93,97,103,106,113,112,117,117,115,118,133,117,71,108,129,138,140,141,148,155,159,165,169,179,195,207,213,222,224,231,248,249,251};

}
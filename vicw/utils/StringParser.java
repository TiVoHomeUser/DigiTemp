package vicw.utils;

/*
 *		Divides a string 
 *
 *
 */
 import java.util.Vector;
 
public class StringParser {
	
	private StringBuffer s;
	private int len = 45;
	private char ws = ' ';
		
	public String[] toStrings() {
		int s_start=0, s_end=0;
		Vector<String> ss = new Vector<String>();
		//
		do{
			//
			if(s_end+len < s.length()){				// break up the string
				for(int i=s_start; i< len; i++){// The last white space in the block
					 if(s.charAt(s_start+i) == ws){
					 	s_end=s_start+i;
					 }
				}
				if(s_end == s_start) s_end = s_start+len;	// no white space found break at len
				//
			}else{								// the last line
				s_end = s.length();
			}
			ss.add(new String(s.substring(s_start,s_end)).trim());
			s_start = s_end;
		}while(s_end < s.length());
		//
		String[] rs = new String[ss.size()];
		for(int i=0; i< rs.length; i++)
			rs[i]=(String) ss.elementAt(i);
		return rs;
	}

	@SuppressWarnings("unused")
	private StringParser() {	// prevent null creation	
	}

	public StringParser(String string) {
		this(string,' ',45);
	}
	public StringParser(String string, char c, int l) {
		s = new StringBuffer(string);
		ws = c;
		len = l;
	}
	
	public static void main(String[] args){
		StringParser sp;
		if(args.length > 0)
			sp = new StringParser(args[0]);
		else
			sp = new StringParser("Hello testing 1234567890 1234567890 1234567890 1234567890 1234567890",' ',20);
		String[] s = sp.toStrings();
		for(int i=0; i < s.length; i++)
			System.out.println("> "+s[i]+" <");
	}
}

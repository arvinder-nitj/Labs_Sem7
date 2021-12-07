package utility;

public class FileArtifactsPair {
		public int fileSize, OutputSize;
		public FileArtifactsPair(int fs,int os){
			fileSize = fs;
			OutputSize=os;
		}
		
		public int getLen() {return fileSize;}
		public int getOS() {return OutputSize;}

	}
import java.io.FileInputStream;
import java.io.FileOutputStream;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.testng.annotations.*;

public class studentsMoreThan70 {
	@BeforeClass //@BeforeClass runs once before the entire test.
	public void setUp() throws Exception {}
	@Test
	public void testImportexport1() throws Exception {
		FileInputStream fi = new FileInputStream("C:\\Users\\arvindersingh\\Desktop\\Labs\\02.SoftwareTesting\\Lab_2\\studentsFile.xls");
		Workbook w = Workbook.getWorkbook(fi);
		Sheet s = w.getSheet(0);
		FileOutputStream fo = new FileOutputStream("C:\\Users\\arvindersingh\\Desktop\\Labs\\02.SoftwareTesting\\Lab_2\\Result_MoreThan70.xls");
		WritableWorkbook wwb = Workbook.createWorkbook(fo);
		WritableSheet ws = wwb.createSheet("result1", 0);
		
		Label lt = new Label(0, 0, "Students With More than 70 in all Subjects");
		ws.addCell(lt);		
		
		int cnt = 0;
		
		for (int i = 1; i < s.getRows(); i++) {
			boolean MoreThan70InAll = true;
			for (int j = 2; j < s.getColumns(); j++){
				String curMarks = s.getCell(j, i).getContents();
				int x=Integer.parseInt(curMarks);
				if(x<70) {
					MoreThan70InAll = false;
					break;
				}
			}
			
			if(MoreThan70InAll) {
				cnt++;
				Label l0 = new Label(0, cnt, s.getCell(1, i).getContents());
				ws.addCell(l0);	
				System.out.println("Records sucessfully updated");
			}		
		}
		wwb.write();
		wwb.close();
	}
}
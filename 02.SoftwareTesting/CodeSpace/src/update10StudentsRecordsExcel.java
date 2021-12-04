//update10StudentsRecordsExcel
import java.io.FileInputStream;
import java.io.FileOutputStream;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.testng.annotations.*;

public class update10StudentsRecordsExcel {
	@BeforeClass //@BeforeClass runs once before the entire test.
	public void setUp() throws Exception {}
	@Test
	public void testImportexport1() throws Exception {
		FileInputStream fi = new FileInputStream("C:\\Users\\arvindersingh\\Desktop\\Labs\\02.SoftwareTesting\\Lab_2\\studentsFile.xls");
		Workbook w = Workbook.getWorkbook(fi);
		Sheet s = w.getSheet(0);
		String a[][] = new String[s.getRows()][s.getColumns()];
		FileOutputStream fo = new FileOutputStream("C:\\Users\\arvindersingh\\Desktop\\Labs\\02.SoftwareTesting\\Lab_2\\Result.xls");
		WritableWorkbook wwb = Workbook.createWorkbook(fo);
		WritableSheet ws = wwb.createSheet("result1", 0);
		
		for (int i = 0; i < s.getRows(); i++) {
			for (int j = 0; j < s.getColumns(); j++){
				a[i][j] = s.getCell(j, i).getContents();
				Label l2 = new Label(j, i, a[i][j]);
				ws.addCell(l2);
			}
		}
		
		//add labels for result
		Label lt = new Label(7, 0, "Total");
		ws.addCell(lt);		
		Label lr = new Label(8, 0, "Result");
		ws.addCell(lr);
		
		
		//find total as well as Pass/fail
		for (int i = 1; i < s.getRows(); i++) {
			boolean passInAll = true;
			int total = 0;
			for (int j = 2; j < s.getColumns(); j++)
			{
				String curMarks = s.getCell(j, i).getContents();
				//a[i][j] = curMarks;
				int x=Integer.parseInt(curMarks);
				
				total+=x;
				if(x<=40) {
					passInAll = false;
					//break;
				}
				/*
				if(x > 35){
					Label l1 = new Label(6, i, "pass");
					ws.addCell(l1);
				}else{
					Label l1 = new Label(6, i, "fail");
					ws.addCell(l1);
					break;
				}
				*/
			}
			Label l0 = new Label(7, i, Integer.toString(total));
			ws.addCell(l0);	
			Label l1 = new Label(8, i, passInAll?"Pass":"Fail");
			ws.addCell(l1);			
			System.out.println("Records sucessfully updated");
		}
		wwb.write();
		wwb.close();
	}
}
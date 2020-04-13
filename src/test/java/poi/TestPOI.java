
package poi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.wyj.application;
import com.baizhi.wyj.entity.User;
import com.baizhi.wyj.mapper.UserMapper;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@SpringBootTest(classes = application.class)
@RunWith(value = SpringRunner.class)
public class TestPOI {

    @Resource
    UserMapper userMapper;
    @Test
    public void name() {
        //创建一个Excel文档
        Workbook workbook = new HSSFWorkbook();
        //创建工作空间
        Sheet sheet = workbook.createSheet("数据表");
        //创建行、从0开始的
        Row row = sheet.createRow(0);
        //创建行内的单元格、从0开始的
        Cell cell = row.createCell(0);
        //设置单元格的内容
        cell.setCellValue("数据信息1");
        //写出单元格
        try {
            ((HSSFWorkbook) workbook).write(new FileOutputStream(new File("D://数据.xls")));
            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void name2() {
        //创建一个Excel文档
        Workbook workbook = new HSSFWorkbook();
        //创建工作空间
        Sheet sheet = workbook.createSheet("数据表");
        //c创建表头、并且合并居中
        Row row2 = sheet.createRow(0);
        Cell cell1 = row2.createCell(0);
        //要合并的列      参数：行开始，行结束，列开时，列结束
        CellRangeAddress region=new CellRangeAddress(0, 0, 0, 7);
        sheet.addMergedRegion(region);
        cell1.setCellValue("用户数据信息表");
        //设置字体的格式
        //构建字体
        HSSFFont font = (HSSFFont) workbook.createFont();
        font.setBold(true);    //加粗
        font.setColor(IndexedColors.GREEN.getIndex()); //颜色
        font.setFontHeightInPoints((short)10);  //字号
        font.setFontName("楷体");  //字体
        //创建样式对象
        CellStyle cellStyle2 = workbook.createCellStyle();
        cellStyle2.setFont(font);     //将字体样式引入
        cellStyle2.setAlignment(HorizontalAlignment.CENTER);  //文字居中
        //创建标题行
        Row row = sheet.createRow(1);
        String[] str = {"ID","姓名","电话","头像","签名","微信","状态","创建日期"};
        for (int i = 0; i < str.length; i++) {
            //单元的下标
            Cell cell = row.createCell(i);
            //单元格的内容
            cell.setCellValue(str[i]);
            //单元格的样式
            String s = str[i];
        }
        //创建样式对象
        CellStyle cellStyle = workbook.createCellStyle();
        //创建日期对象
        DataFormat dataFormat = workbook.createDataFormat();
        //设置日期格式
        cellStyle.setDataFormat(dataFormat.getFormat("yyy年MM月dd日"));
        //查询数据信息
        List<User> users = userMapper.selectAll();
        //处理表格的内容
        for (int i = 0; i < users.size(); i++) {
            //创建行
            Row row1 = sheet.createRow(i + 2);
            //创建单元格一级内容
            row1.createCell(0).setCellValue(users.get(i).getId());
            row1.createCell(1).setCellValue(users.get(i).getUsername());
            row1.createCell(2).setCellValue(users.get(i).getPhone());
            row1.createCell(3).setCellValue(users.get(i).getHeadImg());
            row1.createCell(4).setCellValue(users.get(i).getSign());
            row1.createCell(5).setCellValue(users.get(i).getWechat());
            row1.createCell(6).setCellValue(users.get(i).getStatus());
            Cell cell = row1.createCell(7);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(users.get(i).getCreateDate());
        }
        //写出单元格
        try {
            ((HSSFWorkbook) workbook).write(new FileOutputStream(new File("D://数据.xls")));
            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void name3() {
        //查询数据信息
        List<User> users = userMapper.selectAll();
        for (User user : users) {
            System.out.println();
            user.setHeadImg("D:\\Java\\java_sources\\yingxueApp\\yingxue_wanyj\\src\\main\\webapp\\upload\\image\\1585317463790-阿狸.jpg");
        }
        //参数：标题，表名，实体类类对象，导出的集合
        Workbook  workbook = ExcelExportUtil.exportExcel(new ExportParams("用户的数据信息","数据信息1"),User.class, users);
        //导出表格
        try {
            workbook.write(new FileOutputStream(new File("D://用户数据.xls")));
            workbook.close();
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


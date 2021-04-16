package com.example.exportpdf2;


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.print.PageRange;
import android.print.PrintJob;
import android.print.PrintManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.itextpdf.awt.geom.Point;
import com.itextpdf.text.DocumentException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static Context context;
    private Button button;
    private TextView text;
    private boolean isPermissionPassed = false;
    //網路
    private PrintManager printManager;
    private List<PrintJob> printJobList;
    private PageRange[] pageRanges;
    private PageRange[] writtenPagesRanges;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();
        findView();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPDF();
            }
        });
    }

    private void findView() {
        context = this;
        button = findViewById(R.id.button);
        text = findViewById(R.id.text);
        view = findViewById(R.id.view);
    }

    public void toPDF() {
        PdfItextUtil pdfItextUtil = null;
        try {
            pdfItextUtil = new PdfItextUtil(getSavePdfFilePath())
                    .addTitleToPdf(getTvString(tv_title))
                    .addTextToPdf(getTvString(tv_part1))
                    .addImageToPdfCenterH(getImageFilePath(), 160, 160)
                    .addTextToPdf(getTvString(tv_part2))
                    .addImageToPdfCenterH(getImageFilePath(), 160, 160)
                    .addTextToPdf(getTvString(tv_content));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (pdfItextUtil != null)
                pdfItextUtil.close();
        }
    }

    private void doPrint() {
//        // Get a PrintManager instance
//        PrintManager printManager = (PrintManager) context
//                .getSystemService(Context.PRINT_SERVICE);
//
//        PrintAdapter printAdapter = new PrintAdapter();
//        PrintAttributes.Builder builder = new PrintAttributes.Builder();
//        builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4);//預設紙張大小
//
//        // Set job name, which will be displayed in the print queue
//        String jobName = context.getString(R.string.app_name) + " Document";
//
//
//        if (printManager != null) {
//            // Start a print job, passing in a PrintDocumentAdapter implementation
//            // to handle the generation of a print document
//            PrintJob printJob = printManager.print( jobName, printAdapter, builder.build());//attributes 3rd 可以用作直向橫向參數
//            if (listener!=null) {
//                if(printJob.isCompleted()){
//                    listener.onSuccess();
//                } else {
//                    listener.onFailed();
//                }
//            }
//        } else {
//            if (listener != null) {
//                listener.onFailed();
//            }
//        }

        //方法2
//        printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
//        printJobList = printManager.getPrintJobs();
//        if (!printJobList.isEmpty())
//            writtenPagesRanges = printJobList.get(0).getInfo().getPages();
//        else
//            writtenPagesRanges = new PageRange[0];
//
//        android.print.PrintJob printJob = printManager.print("mDocument", new PrintAdapter(new PrintAdapter.getPrintItemCountListener() {
//            @Override
//            public int getPrintItemCount() {
//                return 24;
//            }
//        }, this, writtenPagesRanges), null);
//        PrintJobInfo printJobInfo = printJob.getInfo();
//        printJobList.add(printJob);
//
//        PageRange[] pageRanges = printJobInfo.getPages();
//        if (writtenPagesRanges.length == 0)
//            writtenPagesRanges = pageRanges;
//        else {
//            /**
//             * 在writtenPagesRanges后追加我们的pageRanges
//             *
//             * */
//        }

        //方法3
//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = windowManager.getDefaultDisplay();


//        //创建PDF文档对象
//        PdfDocument doc = new PdfDocument();
//        //设置文档大小，页面位置，内容区域
//
//        PdfDocument.PageInfo.Builder builder = new PdfDocument.PageInfo.Builder(view.getWidth() + 20, view.getHeight() + 40, 1);
//        Rect rect = new Rect(0, 0, view.getWidth(), view.getHeight());
//        builder.setContentRect(rect);
//        //创建配置信息
//        PdfDocument.PageInfo info = builder.create();
//        //创建页面对象
//        PdfDocument.Page page = doc.startPage(info);
//        //向页面绘制内容
//        View rootView = view.getRootView();
//        rootView.draw(page.getCanvas());
//        doc.finishPage(page);
//        //将PDF文档写入到存储卡Documents公共目录下面
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
//        LocalDateTime now = LocalDateTime.now();
//        Toast.makeText(context, "time = "+dtf.format(now), Toast.LENGTH_SHORT).show();
//
//        String fileName = "/"  + "20210415.pdf";
//        String path = "/sdcard/Download/20210415_3.pdf";
//        path = "/sdcard/Download/"+dtf.format(now)+".pdf";
//
//        try {
//            doc.writeTo(new FileOutputStream(path));
//            Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(context, "失敗", Toast.LENGTH_SHORT).show();
//        }
//        //关闭文档对象，结束
//        doc.close();

        // create a new document
        PdfDocument document = new PdfDocument();

        // create a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(100, 100, 1).create();

        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);

        // draw something on the page
        //View content = getContentView();
        View content = button;

        content.draw(page.getCanvas());

        // finish the page
        document.finishPage(page);

        // add more pages

        // write the document content
        String path = "/sdcard/Download/20210415_3.pdf";
        try {
            document.writeTo(new FileOutputStream(path));
            Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "找不到路徑", Toast.LENGTH_SHORT).show();
        }

        // close the document
        document.close();
    }
    @SuppressLint("ObsoleteSdkInt")
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static Point getScreenSize() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = null;
        if (windowManager != null) {
            display = windowManager.getDefaultDisplay();
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            if (display != null) {
                return new Point(display.getWidth(), display.getHeight());
            } else {
                return null;
            }
        } else {
            Point point = new Point();
            if (display != null) {

                display.getSize(point);
            }
            return point;
        }
    }

    /**
     * 取得將檔案寫入手機的權限
     */
    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        } else {
            isPermissionPassed = true;
        }
    }

    /**
     * 回傳使用者所做的權限選擇(接受/拒絕)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                /**如果用戶同意*/
                isPermissionPassed = true;
            } else {
                /**如果用戶不同意*/
                if (ActivityCompat.shouldShowRequestPermissionRationale(this
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "你搞毛啊！", Toast.LENGTH_SHORT).show();
                    getPermission();
                }
            }
        }
    }//onRequestPermissionsResult
}
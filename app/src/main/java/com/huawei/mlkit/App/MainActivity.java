/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.huawei.mlkit.App;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
//import com.huawei.mlkit.example.asr.AsrAnalyseActivity;
import com.huawei.mlkit.App.bankCard.BcrAnalyseActivity;
import com.huawei.mlkit.App.classification.ImageClassificationAnalyseActivity;
import com.huawei.mlkit.App.document.ImageDocumentAnalyseActivity;
import com.huawei.mlkit.App.face.StillFaceAnalyseActivity;
import com.huawei.mlkit.App.generalCard.GcrAnalyseActivity;
import com.huawei.mlkit.App.IDCard.IcrAnalyseActivity;
import com.huawei.mlkit.App.imgseg.ImageSegmentationStillAnalyseActivity;
//import com.huawei.mlkit.example.landmark.ImageLandmarkAnalyseActivity;
import com.huawei.mlkit.App.object.LiveObjectAnalyseActivity;
//mport com.huawei.mlkit.example.productvisionsearch.ProductVisionSearchAnalyseActivity;
import com.huawei.mlkit.App.text.ImageTextAnalyseActivity;
import com.huawei.mlkit.App.translate.TranslatorActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_SCAN_ONE = 0X01;
    private static final int REQUEST_CODE_DEFINE = 0X0111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        this.findViewById(R.id.btn_face_image).setOnClickListener(this);
        this.findViewById(R.id.btn_text).setOnClickListener(this);
        this.findViewById(R.id.btn_object).setOnClickListener(this);
        this.findViewById(R.id.btn_document).setOnClickListener(this);
        this.findViewById(R.id.btn_classification).setOnClickListener(this);
        this.findViewById(R.id.btn_translate).setOnClickListener(this);
        this.findViewById(R.id.btn_imgseg_image).setOnClickListener(this);

        this.findViewById(R.id.btn_icr).setOnClickListener(this);
        this.findViewById(R.id.btn_bcr).setOnClickListener(this);
        this.findViewById(R.id.btn_gcr).setOnClickListener(this);
        this.findViewById(R.id.button_scan1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_face_image:
                this.startActivity(new Intent(MainActivity.this, StillFaceAnalyseActivity.class));
                break;
            case R.id.btn_classification:
                this.startActivity(new Intent(MainActivity.this, ImageClassificationAnalyseActivity.class));
                break;
            case R.id.btn_object:
                this.startActivity(new Intent(MainActivity.this, LiveObjectAnalyseActivity.class));
                break;
            case R.id.btn_document:
                this.startActivity(new Intent(MainActivity.this, ImageDocumentAnalyseActivity.class));
                break;
            case R.id.btn_text:
                this.startActivity(new Intent(MainActivity.this, ImageTextAnalyseActivity.class));
                break;
            case R.id.btn_translate:
                this.startActivity(new Intent(MainActivity.this, TranslatorActivity.class));
                break;
            case R.id.btn_imgseg_image:
                this.startActivity(new Intent(MainActivity.this, ImageSegmentationStillAnalyseActivity.class));
                break;
            case R.id.btn_icr:
                this.startActivity(new Intent(MainActivity.this, IcrAnalyseActivity.class));
                break;
            case R.id.btn_bcr:
                this.startActivity(new Intent(MainActivity.this, BcrAnalyseActivity.class));
                break;
            case R.id.btn_gcr:
                this.startActivity(new Intent(MainActivity.this, GcrAnalyseActivity.class));
                break;
            case R.id.button_scan1: // Scan
                ScanUtil.startScan(this, REQUEST_CODE_SCAN_ONE, new HmsScanAnalyzerOptions.Creator().create());
                break;
            case R.id.button_scan2: // QR Code generate
                // 启动活动 “GenerateCodeActivity”
                this.startActivity(new Intent(MainActivity.this, GenerateCodeActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //扫码页面结束后，处理扫码结果
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        //Default View
        if (requestCode == REQUEST_CODE_SCAN_ONE) {
            HmsScan obj = data.getParcelableExtra(ScanUtil.RESULT);   //从onActivityResult返回data中，用 ScanUtil.RESULT作为key值取到HmsScan返回值
            if (obj != null) {
                if (!TextUtils.isEmpty(((HmsScan) obj).getOriginalValue())) {
                    Toast.makeText(this, ((HmsScan) obj).getOriginalValue(), Toast.LENGTH_SHORT).show(); //Toast 显示结果
                }
            }
            //Customized View
        } else if (requestCode == REQUEST_CODE_DEFINE) {
            HmsScan obj = data.getParcelableExtra(DefinedActivity.SCAN_RESULT);
            if (obj != null) {
                if (!TextUtils.isEmpty(((HmsScan) obj).getOriginalValue())) {
                    Toast.makeText(this, ((HmsScan) obj).getOriginalValue(), Toast.LENGTH_SHORT).show(); //Toast 显示结果
                }
            }
        }

    }
}

package com.arramton.closet.rider.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arramton.cakingom.utils.UriToFile
import com.arramton.closet.restService.RetrofitBuilder
import com.arramton.closet.rider.R
import com.arramton.closet.rider.factory.OrderFactory
import com.arramton.closet.rider.repository.OrderRepository
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.rider.viewModel.OrderViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream

class UploadDocumentsActivity : AppCompatActivity() {
    private lateinit var uploadBtnRegisterBtn:MaterialButton
    private lateinit var etAddharNumber:EditText
    private lateinit var etPanNumber:EditText
    private lateinit var backBtn : MaterialButton
    private lateinit var layoutAddharFront:LinearLayout;
    private lateinit var layoutAddharBack:LinearLayout
    private lateinit var layoutPanCard:LinearLayout
    private var aadharFrontVar = false
    private var aadharBackVar = false
    private var panFrontVar = false
    private lateinit var name:String
    private lateinit var mobile:String
    private lateinit var email:String
    private lateinit var gender:String
    private lateinit var address:String
    private lateinit var landMark:String
    private lateinit var state:String
    private lateinit var city:String
    private lateinit var pincode:String
    private  val pic_id = 123
    private val pickImage = 100
    private var imageUri: Uri? = null
    private lateinit var imageType:String

    private lateinit var frontMultipart:MultipartBody.Part

    private lateinit var backMultiPart:MultipartBody.Part

    private lateinit var pancardMultipart:MultipartBody.Part

    private lateinit var uplaodAddharFrontCL:CoordinatorLayout
    private lateinit var uplaodAddharBackCL:CoordinatorLayout
    private lateinit var uplaodPancardCL:CoordinatorLayout
    private lateinit var imgAdharFront:ImageView
    private lateinit var imgAdharBack:ImageView
    private lateinit var imgPancard:ImageView
    private lateinit var imgFrontDel:ImageView
    private lateinit var imgBackDel:ImageView
    private lateinit var imgPancardDel:ImageView
    private  var adharCardFront:String=""
    private  var adharCardBack:String=""
    private  var pancard:String=""
    private lateinit var orderRepository: OrderRepository
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var apiInterface: ApiInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_documents)
        init()
    }

    fun init(){

        apiInterface= RetrofitBuilder.getInstance(application)!!.api
        orderRepository= OrderRepository(apiInterface,this,application)
        orderViewModel= ViewModelProvider(this, OrderFactory(orderRepository)).get(OrderViewModel::class.java)

        name=intent.getStringExtra("name").toString()

        mobile=intent.getStringExtra("mobile").toString()

        email=intent.getStringExtra("email").toString()

        gender=intent.getStringExtra("gender").toString()

        address=intent.getStringExtra("address").toString()

        landMark=intent.getStringExtra("landMark").toString()

        state=intent.getStringExtra("state").toString()

        city=intent.getStringExtra("city").toString()

        pincode=intent.getStringExtra("pincode").toString()


        uplaodAddharFrontCL=findViewById(R.id.upload_document_addhar_front_layout)

        uplaodAddharBackCL=findViewById(R.id.upload_document_addhar_back_layout)

        uplaodPancardCL=findViewById(R.id.upload_document_pancard_layout)

        imgAdharFront=findViewById(R.id.upload_document_addhar_front_img)

        imgAdharBack=findViewById(R.id.upload_document_addhar_back_img)

        imgPancard=findViewById(R.id.upload_document_pancard_img)

        etAddharNumber=findViewById(R.id.upload_document_addhar_number)

        backBtn = findViewById(R.id.backBtn)



        layoutAddharFront=findViewById(R.id.upload_addhar_front_layout)

        layoutAddharFront.setOnClickListener {
            imageType="front"
            openCameraBottomSheet()
        }

        layoutAddharBack=findViewById(R.id.upload_addhar_back_layout)

        layoutAddharBack.setOnClickListener {
            imageType="back"
            openCameraBottomSheet()
        }

        layoutPanCard=findViewById(R.id.upload_addhar_pancard_layout)

        layoutPanCard.setOnClickListener {
            imageType="pancard"
            openCameraBottomSheet()
        }

        backBtn.setOnClickListener {
            onBackPressed()
        }

        etPanNumber=findViewById(R.id.upload_document_pancard_number)

        uploadBtnRegisterBtn=findViewById(R.id.upload_document_register)

        imgFrontDel=findViewById(R.id.adhar_front_del)
        imgBackDel=findViewById(R.id.adhar_back_del)
        imgPancardDel=findViewById(R.id.pancard_del)

        imgFrontDel.setOnClickListener {
//            frontMultipart=null
            layoutAddharFront.visibility=View.VISIBLE
            uplaodAddharFrontCL.visibility=View.GONE
        }

        imgBackDel.setOnClickListener {
            layoutAddharBack.visibility=View.VISIBLE
            uplaodAddharBackCL.visibility=View.GONE
        }


        imgPancard.setOnClickListener {
            layoutPanCard.visibility=View.VISIBLE
            uplaodPancardCL.visibility=View.GONE
        }


        orderViewModel.uploadObserver.observe(this@UploadDocumentsActivity, Observer {

            if(it!=null){
                if(imageType.equals("pancard")){
                    pancard=it.data

                }else if(imageType.equals("front")){
                    adharCardFront=it.data
                }else if (imageType.equals("back")){
                    adharCardBack=it.data
                }
            }
        })


        uploadBtnRegisterBtn.setOnClickListener {

            if (etAddharNumber.text.isEmpty()){
                toast("Please select Aadhar number")
                return@setOnClickListener
            }else if(layoutAddharFront.isVisible){
                toast("Please select front Aadhar picture")
                return@setOnClickListener
            }else if(layoutAddharBack.isVisible){
                toast("Please select back Aadhar picture")
                return@setOnClickListener
            }else if(etPanNumber.text.isEmpty()){
                toast("Please select Pan number")
                return@setOnClickListener
            }else if(layoutPanCard.isVisible){
                toast("Please select pan card picture")
                return@setOnClickListener
            }

            else {
                val intent: Intent =
                    Intent(this@UploadDocumentsActivity, BankDetailsActivity::class.java)
                intent.putExtra("name", name)
                intent.putExtra("mobile", mobile)
                intent.putExtra("email", email)
                intent.putExtra("gender", gender)
                intent.putExtra("address", address)
                intent.putExtra("landMark", landMark)
                intent.putExtra("state", state)
                intent.putExtra("city", city)
                intent.putExtra("pincode", pincode)
                intent.putExtra("front", adharCardFront)
                intent.putExtra("back", adharCardBack)
                intent.putExtra("pancard", pancard)
                intent.putExtra("addharNumber", etAddharNumber.text.toString())
                intent.putExtra("pancardNumber", etPanNumber.text.toString())
                startActivity(intent)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pic_id) {
            val photo = data!!.extras!!["data"] as Bitmap?
            val bytes = ByteArrayOutputStream()
            photo?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path: String = MediaStore.Images.Media.insertImage(this@UploadDocumentsActivity.getContentResolver(), photo, "image", null)

            if (imageType.equals("front")){


                val file = UriToFile(this@UploadDocumentsActivity).getImageBody(Uri.parse(path))
                val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                 frontMultipart = MultipartBody.Part.createFormData("image", file.name, requestFile)

                if (frontMultipart!=null){
                    layoutAddharFront.visibility=View.GONE
                    uplaodAddharFrontCL.visibility=View.VISIBLE
                    imgAdharFront.setImageBitmap(photo)

//                    orderViewModel.uploadObserver.observe(this@UploadDocumentsActivity, Observer {
//
//                        if(it!=null){
//                           adharCardFront=it.data
//                        }
//                    })
                    orderViewModel.uploadObservable(frontMultipart)

//                    Glide.with(this@UploadDocumentsActivity).load(photo).into(imgAdharBack)

                }


            }else if (imageType.equals("back")){
//                val photo = data!!.extras!!["data"] as Bitmap?
//                val bytes = ByteArrayOutputStream()
//                photo?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//                val path: String = MediaStore.Images.Media.insertImage(this@UploadDocumentsActivity.getContentResolver(), photo, "Title", null)

                val file = UriToFile(this@UploadDocumentsActivity).getImageBody(Uri.parse(path))
                val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                backMultiPart = MultipartBody.Part.createFormData("image", file.name, requestFile)

                if (backMultiPart!=null){
                    layoutAddharBack.visibility=View.GONE
                    uplaodAddharBackCL.visibility=View.VISIBLE
                    imgAdharBack.setImageBitmap(photo)

//                    orderViewModel.uploadObserver.observe(this@UploadDocumentsActivity, Observer {
//
//                        if(it!=null){
//                            adharCardBack=it.data
//                        }
//                    })
                    orderViewModel.uploadObservable(backMultiPart)
//                    Glide.with(this@UploadDocumentsActivity).load(file).into(imgAdharBack)
                }


            }else if (imageType.equals("pancard")){

                val file = UriToFile(this@UploadDocumentsActivity).getImageBody(Uri.parse(path))
                val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                pancardMultipart = MultipartBody.Part.createFormData("image", file.name, requestFile)
                if(pancardMultipart!=null){

                    layoutPanCard.visibility=View.GONE
                    uplaodPancardCL.visibility=View.VISIBLE
                    imgPancard.setImageBitmap(photo)

                    orderViewModel.uploadObservable(pancardMultipart)


                }

            }

        }else if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data

            val file = UriToFile(this@UploadDocumentsActivity).getImageBody(imageUri)
            val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

            if (imageType.equals("front")){

//                val file = UriToFile(this@UploadDocumentsActivity).getImageBody(imageUri)
//                val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                frontMultipart = MultipartBody.Part.createFormData("aadhaar_front", file.name, requestFile)

                if (frontMultipart!=null){
                    layoutAddharFront.visibility=View.GONE
                    uplaodAddharFrontCL.visibility=View.VISIBLE
                    imgAdharFront.setImageURI(imageUri)
                    aadharFrontVar = true

//                    orderViewModel.uploadObserver.observe(this@UploadDocumentsActivity, Observer {
//
//                        if(it!=null){
//                            adharCardFront=it.data
//                        }
//                    })
                    orderViewModel.uploadObservable(frontMultipart)



//                    Glide.with(this@UploadDocumentsActivity).load(imageUri).into(imgAdharBack)

                }


            }else if (imageType.equals("back")){

//                val file = UriToFile(this@UploadDocumentsActivity).getImageBody(imageUri)
//                val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                backMultiPart = MultipartBody.Part.createFormData("aadhaar_back", file.name, requestFile)
//                imageUri = data?.data

                if (backMultiPart!=null){
                    layoutAddharBack.visibility=View.GONE
                    uplaodAddharBackCL.visibility=View.VISIBLE
                    imgAdharBack.setImageURI(imageUri)
                    aadharBackVar = true
//                    orderViewModel.uploadObserver.observe(this@UploadDocumentsActivity, Observer {
//
//                        if(it!=null){
//                            adharCardBack=it.data
//                        }
//                    })
                    orderViewModel.uploadObservable(backMultiPart)


                }



            }else if (imageType.equals("pancard")){
//                val file = UriToFile(this@UploadDocumentsActivity).getImageBody(imageUri)
//                val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                pancardMultipart = MultipartBody.Part.createFormData("pan_picture", file.name, requestFile)
//                imageUri = data?.data

                if(pancardMultipart!=null){

                    layoutPanCard.visibility=View.GONE
                    uplaodPancardCL.visibility=View.VISIBLE
                    imgPancard.setImageURI(imageUri)
                    panFrontVar = true

                    orderViewModel.uploadObserver.observe(this@UploadDocumentsActivity, Observer {

                        if(it!=null){
                            pancard=it.data
                        }
                    })
                    orderViewModel.uploadObservable(pancardMultipart)

                }
            }

        }
    }

    fun openCameraBottomSheet(){
        var dialog = BottomSheetDialog(this)

        val view = layoutInflater.inflate(R.layout.custom_bottom_photo_sheet, null)
        val openCamera=view.findViewById<TextView>(R.id.custom_photo_open_camera)
        val openCancel=view.findViewById<TextView>(R.id.custom_photo_cancel)
        val openGallery=view.findViewById<TextView>(R.id.custom_photo_gallery)
        openCamera.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (takePictureIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(takePictureIntent, pic_id)
                dialog.dismiss()
            }
        }

        openGallery.setOnClickListener {
//
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            if (gallery.resolveActivity(packageManager)!=null){
                startActivityForResult(gallery, 100)
                dialog.dismiss()
            }

        }

        openCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(true)

        dialog.setContentView(view)

        dialog.show()

    }

    private fun toast(msg: String){
        Toast.makeText(this@UploadDocumentsActivity, msg, Toast.LENGTH_SHORT).show()
    }
}
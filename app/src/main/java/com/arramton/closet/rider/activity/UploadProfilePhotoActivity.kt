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
import com.arramton.closet.rider.factory.AuthFactory
import com.arramton.closet.rider.factory.OrderFactory
import com.arramton.closet.rider.repository.AuthRepository
import com.arramton.closet.rider.repository.OrderRepository
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.rider.viewModel.AuthViewModel
import com.arramton.closet.rider.viewModel.OrderViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.http.Multipart
import java.io.ByteArrayOutputStream

class UploadProfilePhotoActivity : AppCompatActivity() {
    private lateinit var name: String
    private lateinit var mobile: String
    private lateinit var email: String
    private lateinit var gender: String
    private lateinit var address: String
    private lateinit var landMark: String
    private lateinit var state: String
    private lateinit var city: String
    private lateinit var pincode: String
    private lateinit var bankName: String
    private lateinit var accountHolderName: String
    private lateinit var accountNumber: String
    private lateinit var IFSCCode: String
    private lateinit var adharNumber: String
    private lateinit var panNumber: String
    private lateinit var front: String
    private lateinit var back: String
    private lateinit var pancard: String
    private lateinit var uploadPhotoLayout: LinearLayout
    private lateinit var profileCL: CoordinatorLayout
    private var imageUri: Uri? = null
    private lateinit var profileMultipart: MultipartBody.Part
    private lateinit var imgProfile: ImageView

    private lateinit var orderRepository: OrderRepository
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var apiInterface: ApiInterface

    private val pic_id = 123
    private val pickImage = 100

    private var profileUrl = ""

    private lateinit var authRepository: AuthRepository
    private lateinit var authViewModel: AuthViewModel

    private lateinit var profileRegisterBtb: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_profile_photo)

        init()
    }

    fun init() {

        apiInterface = RetrofitBuilder.getInstance(application)!!.api

        orderRepository = OrderRepository(apiInterface, this, application)

        orderViewModel =
            ViewModelProvider(this, OrderFactory(orderRepository)).get(OrderViewModel::class.java)

        authRepository = AuthRepository(this, apiInterface, application)

        authViewModel =
            ViewModelProvider(this, AuthFactory(authRepository)).get(AuthViewModel::class.java)

        name = intent.getStringExtra("name").toString()

        mobile = intent.getStringExtra("mobile").toString()

        email = intent.getStringExtra("email").toString()

        gender = intent.getStringExtra("gender").toString()

        address = intent.getStringExtra("address").toString()

        landMark = intent.getStringExtra("landMark").toString()

        state = intent.getStringExtra("state").toString()

        city = intent.getStringExtra("city").toString()

        pincode = intent.getStringExtra("pincode").toString()

        front = intent.getStringExtra("front").toString()

        back = intent.getStringExtra("back").toString()

        pancard = intent.getStringExtra("pancard").toString()

        adharNumber = intent.getStringExtra("addharNumber").toString()

        panNumber = intent.getStringExtra("pancardNumber").toString()

        bankName = intent.getStringExtra("bankName").toString()

        accountHolderName = intent.getStringExtra("holderName").toString()

        accountNumber = intent.getStringExtra("accountNumber").toString()

        IFSCCode = intent.getStringExtra("ifscCode").toString()

        uploadPhotoLayout = findViewById(R.id.upload_profile_photo_layout)

        profileCL = findViewById(R.id.upload_profile_photo_cl)

        imgProfile = findViewById(R.id.profile_img)

        profileRegisterBtb = findViewById(R.id.profile_register_btn)

        profileRegisterBtb.setOnClickListener {
            if (uploadPhotoLayout.isVisible) {
                Toast.makeText(
                    this@UploadProfilePhotoActivity,
                    "Please Upload Photo",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else
                register();
        }

        uploadPhotoLayout.setOnClickListener {
            openCameraBottomSheet()
        }

        authViewModel.registerObservable.observe(this, Observer {
            if (it != null) {
                if (it.success) {
                    Toast.makeText(this@UploadProfilePhotoActivity, it.message, Toast.LENGTH_SHORT)
                        .show()
                    startActivity(
                        Intent(
                            this@UploadProfilePhotoActivity,
                            LoginActivity::class.java
                        )
                    )
                    finishAffinity()
                }
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pic_id) {
            val photo = data!!.extras!!["data"] as Bitmap?
            val bytes = ByteArrayOutputStream()
            photo?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path: String = MediaStore.Images.Media.insertImage(
                this@UploadProfilePhotoActivity.getContentResolver(),
                photo,
                "image",
                null
            )

            val file = UriToFile(this@UploadProfilePhotoActivity).getImageBody(Uri.parse(path))
            val requestFile: RequestBody =
                file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            profileMultipart = MultipartBody.Part.createFormData("image", file.name, requestFile)

            if (profileMultipart != null) {
                uploadPhotoLayout.visibility = View.GONE
                profileCL.visibility = View.VISIBLE
                imgProfile.setImageBitmap(photo)
                orderViewModel.uploadObserver.observe(this@UploadProfilePhotoActivity, Observer {

                    if (it != null) {
                        profileUrl = it.data

                    }
                })
                orderViewModel.uploadObservable(profileMultipart)

            } else {

            }


        } else if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            val file = UriToFile(this@UploadProfilePhotoActivity).getImageBody(imageUri)
            val requestFile: RequestBody =
                file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            profileMultipart = MultipartBody.Part.createFormData("image", file.name, requestFile)
            if (profileMultipart != null) {
                uploadPhotoLayout.visibility = View.GONE
                profileCL.visibility = View.VISIBLE
                imgProfile.setImageURI(imageUri)
                orderViewModel.uploadObserver.observe(this@UploadProfilePhotoActivity, Observer {

                    if (it != null) {
                        profileUrl = it.data
                    }
                })
                orderViewModel.uploadObservable(profileMultipart)
            } else {

            }

        }
    }

    fun openCameraBottomSheet() {
        var dialog = BottomSheetDialog(this)

        val view = layoutInflater.inflate(R.layout.custom_bottom_photo_sheet, null)
        val openCamera = view.findViewById<TextView>(R.id.custom_photo_open_camera)
        val openCancel = view.findViewById<TextView>(R.id.custom_photo_cancel)
        val openGallery = view.findViewById<TextView>(R.id.custom_photo_gallery)
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
            if (gallery.resolveActivity(packageManager) != null) {
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

    fun register() {

        authViewModel.registerObserver(
            name,
            mobile,
            email,
            gender,
            address,
            landMark,
            state,
            city,
            pincode,
            adharNumber,
            front,
            back,
            panNumber,
            pancard,
            bankName,
            accountHolderName,
            accountNumber,
            IFSCCode,
            profileUrl
        )
    }
}
package com.arramton.closet.rider.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arramton.cakingom.utils.UriToFile
import com.arramton.closet.restService.RetrofitBuilder
import com.arramton.closet.rider.R
import com.arramton.closet.rider.adapter.EditChildOrderDetailsAdapter
import com.arramton.closet.rider.adapter.EditParentOrderDetailsAdapter
import com.arramton.closet.rider.factory.OrderFactory
import com.arramton.closet.rider.listener.EditSubChildListener
import com.arramton.closet.rider.listener.OrderDetailsListener
import com.arramton.closet.rider.model.newOrder.editNewOrder.EditNewOrderRequest
import com.arramton.closet.rider.model.newOrder.editNewOrder.Item
import com.arramton.closet.rider.model.orderDetails.CostumesOrderItem
import com.arramton.closet.rider.model.orderDetails.Order
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
import kotlin.math.cos

class EditNewJobActivity : AppCompatActivity() {
    private lateinit var imgBackBtn:ImageView
    private lateinit var apiInterface: ApiInterface
    private lateinit var orderRepository: OrderRepository
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var  id:String
    private lateinit var editParentOrderDetailsAdapter: EditParentOrderDetailsAdapter
    private lateinit var rvParent:RecyclerView
    private lateinit var rvChild:RecyclerView
    private lateinit var orderDetailsChildCategoryAdapter: EditChildOrderDetailsAdapter
    private lateinit var tvUploadOrder:TextView
    private lateinit var itemList: ArrayList<Item>
    private lateinit var remarkValue:String
    private lateinit var coustumeIdValue:String
    private lateinit var OrderItemIdValue:String
    private lateinit var imageValue:String
    private  var uid:String=""
    private var url:String=""
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_new_job)
        init()
    }

    fun init(){

        itemList= ArrayList()

        tvUploadOrder=findViewById(R.id.edit_new_job_update_order)
        rvParent=findViewById(R.id.edit_new_job_parent)
        rvParent.setHasFixedSize(false)
        rvParent.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        rvChild=findViewById(R.id.edit_new_job_child)
        rvChild.setHasFixedSize(false)
        rvChild.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)


        id=intent.getStringExtra("id").toString()

        imgBackBtn=findViewById(R.id.edit_new_job_back_btn)
        imgBackBtn.setOnClickListener {
            onBackPressed()
        }

        apiInterface= RetrofitBuilder.getInstance(application)!!.api
        orderRepository= OrderRepository(apiInterface,this,application)
        orderViewModel= ViewModelProvider(this, OrderFactory(orderRepository)).get(OrderViewModel::class.java)
        orderViewModel.orderDetailsLiveData.observe(this, Observer {

            if (it!=null){
                if(it.success){


                    editParentOrderDetailsAdapter= EditParentOrderDetailsAdapter(this,it.data.orderItem,object :
                        OrderDetailsListener { override fun listAdd(list1: List<CostumesOrderItem>) {
                            orderDetailsChildCategoryAdapter= EditChildOrderDetailsAdapter(this@EditNewJobActivity,list1,object :EditSubChildListener{
                                override fun onClickOpenCamera(imgeId: Int) {
                                    openCameraBottomSheet()
                                }

                                override fun onClick(
                                    remark: String,
                                    costume_id: Int,
                                    order_item_id: Int,
                                    image: String
                                ) {
                                    remarkValue=remark
                                    coustumeIdValue= costume_id.toString()
                                    OrderItemIdValue=order_item_id.toString()
                                    imageValue=image



                                }
                            })
                            rvChild.adapter=orderDetailsChildCategoryAdapter

                        }
                    })
                    rvParent.adapter=editParentOrderDetailsAdapter


                }
            }
        })
        orderViewModel.orderDetails(id)

        orderViewModel.editNewJobObserver.observe(this, Observer {
            if (it!=null){
                if (it.success){
                    Toast.makeText(this@EditNewJobActivity,it.message,Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@EditNewJobActivity,PickupOrderActivity::class.java))
                }
            }
        })

        tvUploadOrder.setOnClickListener {
            uploadOrder()
        }
    }

    fun uploadOrder(){
        val item=Item(coustumeIdValue,imageValue,OrderItemIdValue,remarkValue)

        itemList.add(item)
        val editNewOrderRequest:EditNewOrderRequest=EditNewOrderRequest(itemList,Integer.parseInt(id))

        orderViewModel.editNewOrderObservable(editNewOrderRequest)
    }

    companion object {
        // Define the pic id
        private const val pic_id = 123
        private val pickImage = 100

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pic_id) {
            val photo = data!!.extras!!["data"] as Bitmap?

            val bytes = ByteArrayOutputStream()
            photo?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path: String = MediaStore.Images.Media.insertImage(this@EditNewJobActivity.getContentResolver(), photo, "Title", null)

            val file = UriToFile(this@EditNewJobActivity).getImageBody(Uri.parse(path))
            val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
//
//            cakeLayout.visibility= View.VISIBLE
//            tvProfilePick.visibility= View.GONE
//            imgProfileBtn.setImageBitmap(photo)

//            Glide.with(this@ProductDetailsActivity).load(photo).load(imgProfileBtn)


//            productDetailsViewModel.photoCakeLiveData(body)
//            productDetailsViewModel.photoCakeSave.observe(this, Observer {
//                if (it!=null){
//                    Toast.makeText(this@ProductDetailsActivity,it.message,Toast.LENGTH_SHORT).show()
//                    Glide.with(this@ProductDetailsActivity).load(it.data.url).load(imgProfileBtn)
//
//                    uid=it.data.uid
//                    url=it.data.url
//
//                }
//            })
        }else if (resultCode == RESULT_OK && requestCode == pickImage) {
//            cakeLayout.visibility= View.VISIBLE
//            tvProfilePick.visibility= View.GONE
//            imageUri = data?.data
//            imgProfileBtn.setImageURI(imageUri)


            val file = UriToFile(this@EditNewJobActivity).getImageBody(imageUri)
            val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

//
//            val builder: MultipartBody.Builder = MultipartBody.Builder().setType(MultipartBody.FORM)
//
////            builder.addFormDataPart("first_name", firstName) // whatever data you will pass to the the request body
//                .addFormDataPart("profile_photo", file.name, requestFile) // the profile photo
//            // make sure the name (ie profile_photo), matches your api, that is name of the key.
//
//
//            val requestBody: RequestBody = builder.build()

//            val file = File(Environment.getExternalStorageDirectory(), imageUri.toString())
//            val uri = Uri.fromFile(file)
//            val auxFile = File(uri.toString())

//            val file: File =
//                File(ProductDetailsActivity.getRealPathFromUri(this@ProductDetailsActivity, data!!.data))
//
//             RequestBody requestBody=RequestBody.create(MediaType.parse("image/jpg"),getRealPathFromUri(context, data.getData()));
//
//             RequestBody requestBody=RequestBody.create(MediaType.parse("image/jpg"),getRealPathFromUri(context, data.getData()));
//            val requestBody = RequestBody.create(Media.parse("image/jpeg"), file)
//            multiBody = createFormData.createFormData("file", file.name, requestBody)
//
//            productDetailsViewModel.photoCakeLiveData(body)
//            productDetailsViewModel.photoCakeSave.observe(this, Observer {
//                if (it!=null){
//                    Toast.makeText(this@ProductDetailsActivity,it.message,Toast.LENGTH_SHORT).show()
//                    Glide.with(this@ProductDetailsActivity).load(it.data.url).load(imgProfileBtn)
//                    uid=it.data.uid
//                    url=it.data.url
//                }else{
//
//                }
//            })
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

}
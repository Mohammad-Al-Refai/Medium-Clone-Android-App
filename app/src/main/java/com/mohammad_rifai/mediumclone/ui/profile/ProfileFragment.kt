package com.mohammad_rifai.mediumclone.ui.fovorite

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import app_networking.Activation
import app_networking.Connector
import com.mohammad_rifai.mediumclone.MainActivity
import com.mohammad_rifai.mediumclone.R
import com.mohammad_rifai.mediumclone.Register
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.bio_dialog.*
import kotlinx.android.synthetic.main.change_password.*
import kotlinx.android.synthetic.main.content_profile.*
import kotlinx.android.synthetic.main.content_profile.view.*
import kotlinx.android.synthetic.main.profile_item.view.*
import org.json.JSONObject
import java.io.File

val pakegName_Global="com.mohammad_rifai.mediumclone"
var changeImage=ChangeImage()
var image_url=""
class ProfileFragment : Fragment() {

    private lateinit var slideshowViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)


        val root = inflater.inflate(R.layout.activity_profile, container, false)
        var path= Environment.getExternalStorageDirectory().toString()+"/Android/data/${pakegName_Global}"
        File(path).mkdir()
        var active=Activation(root.context, pakegName_Global)
        var connector=Connector(root.context, pakegName_Global)
        var progress= ProgressDialog(root.context)
        progress.setMessage("Loading...")
        progress.show()
        active.getToken {
            if(it[0] as Boolean){
                connector.Check(it[1] as String){arr->
                    if (arr[0] as Boolean){
                        var data=JSONObject(arr[2].toString())
                        image_url=data.get("image").toString()
                        Picasso.get().load(connector.API+data.get("image").toString()).into(root.profile_image)
                        var anim= AnimationUtils.loadAnimation(root.context,R.anim.fade_in_text)
                        root.profile_image.startAnimation(anim)
                        root.tv_username.text=data.get("name").toString()
                        if(data.get("bio").toString()!=="false"){
                            root.tv_bio.text=data.get("bio").toString()
                        }

                        Toast.makeText(root.context,arr[1].toString(),Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(root.context,arr[1].toString(),Toast.LENGTH_LONG).show()
                    }
                }
            }else{

            }

        }
        active.IsLogging {
            var result=it as Array<Any>
            if(result[0] as Boolean){
                progress.dismiss()
                // Download all user data
                var arr= arrayListOf(arrayOf("Change image profile",R.drawable.ic_baseline_image_24)
                ,arrayOf("Change bio",R.drawable.ic_baseline_text_snippet_24),arrayOf("Change password",R.drawable.ic_baseline_lock_24))
                var item=ItemAdapter(root.context,arr)
                list_view.adapter=item
            list_view.setOnItemClickListener { parent, view, position, id ->
                var alert=Dialog(root.context,android.R.style.Theme_DeviceDefault_Light_NoActionBar)
              if(position==0){
                  alert.setContentView(R.layout.change_image)
                  alert.show()
                  alert.findViewById<Button>(R.id.choose).setOnClickListener {
                      val intent = Intent(Intent.ACTION_GET_CONTENT)
                      intent.type = "image/*"
                      startActivityForResult(intent, 0)
                      changeImage.image_view= alert.findViewById<ImageView>(R.id.test_image)
                        changeImage.IsHaveImage=true
                  }

                  alert.findViewById<Button>(R.id.discard).setOnClickListener {
                      alert.dismiss()
                  }
                  alert.findViewById<Button>(R.id.save).setOnClickListener {
                      var progress2= ProgressDialog(root.context)
                      progress2.setMessage("Loading")
                      progress2.show()
                            activity?.contentResolver?.openInputStream(changeImage.image?.data!!).use {base->
                              var base64=  Base64.encodeToString(base?.readBytes(), Base64.DEFAULT)
                                active.getToken {arr->
                                    if(arr[0]as Boolean){
                                        connector.update_image(base64,arr[1].toString()){
                                            progress2.dismiss()
                                            var A=AlertDialog.Builder(root.context)
                                            A.setMessage(it[1].toString())
                                            A.show()
                                            alert.dismiss()

                                            Picasso.get().load(connector.API+image_url).into(root.profile_image)
                                            Toast.makeText(root.context,image_url,Toast.LENGTH_LONG).show()
                                            var anim= AnimationUtils.loadAnimation(root.context,R.anim.fade_in_text)
                                            root.profile_image.startAnimation(anim)
                                        }
                                    }else{
                                        progress2.dismiss()
                                       Toast.makeText(root.context,"You can't change image !",Toast.LENGTH_LONG).show()
                                    }

                                }
                            }
                  }
              }
            if (position==2){

                alert.setContentView(R.layout.change_password)
                alert.show()

                alert.button3.setOnClickListener {
                    var old_pass=alert.findViewById<EditText>(R.id.editTextTextPassword).text.toString()
                    var new_pass=alert.findViewById<EditText>(R.id.editTextTextPassword2).text.toString()
                    active.getToken {
                        if (it[0] as Boolean){
                            connector.update_password(old_pass,new_pass,it[1].toString()){s->
                                Toast.makeText(root.context,s[1].toString(),Toast.LENGTH_LONG).show()
                            }
                        }else{
                                Toast.makeText(root.context,it[1].toString(),Toast.LENGTH_LONG).show()

                        }

                    }

                }
            }
                if (position==1){

                    alert.setContentView(R.layout.bio_dialog)
                    alert.show()

                    alert.save_bio.setOnClickListener {
                        var bio=alert.findViewById<EditText>(R.id.bio).text.toString()
                        active.getToken {
                            if (it[0] as Boolean){
                                connector.update_bio(bio,it[1].toString()){s->
                                    Toast.makeText(root.context,s[1].toString(),Toast.LENGTH_LONG).show()
                                }
                            }else{
                                Toast.makeText(root.context,it[1].toString(),Toast.LENGTH_LONG).show()

                            }

                        }

                    }
                }
            }
            }else{
                progress.dismiss()
                Toast.makeText(root.context,"you must have account to see your profile and edit",Toast.LENGTH_LONG).show()
                var intent=Intent(root.context,Register::class.java)
                startActivity(intent)
            }
        }



        var anim= AnimationUtils.loadAnimation(root.context,R.anim.fade_in_text)
        root.startAnimation(anim)

            // Inflate the menu; this adds items to the action bar if it is present.



        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==-1){
            changeImage.loadImage(data)
            changeImage.setImage()

        }else{
            changeImage.IsHaveImage=false
        }
    }
}


class ChangeImage(){
    var IsHaveImage=false
    var image:Intent?=null
    var image_view:ImageView?=null
    fun loadImage(image:Intent?){
        this.image=image
    }
    fun setImage(){
        if(IsHaveImage){
            Picasso.get().load(image?.data).into(image_view)
        }

    }
}


class ItemAdapter(context: Context, data: ArrayList<Array<Any>>): BaseAdapter() {
    var data= data
    var context=context
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view =inflater.inflate(R.layout.profile_item,null)
        view.tv_title.text=data[position][0].toString()
        when (position){
            0-> Picasso.get().load(R.drawable.ic_baseline_image_24).into(view.tv_image)
            1-> Picasso.get().load(R.drawable.ic_baseline_text_snippet_24).into(view.tv_image)
            2-> Picasso.get().load(R.drawable.ic_baseline_lock_24).into(view.tv_image)
        }


        return view
    }

    override fun getItem(position: Int): Any {
      return data[position]
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getCount(): Int {
       return data.size
    }

}
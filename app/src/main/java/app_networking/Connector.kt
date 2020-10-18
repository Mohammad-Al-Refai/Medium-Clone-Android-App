package app_networking

import android.content.Context
import android.os.Environment
import android.util.Base64
import android.util.JsonToken
import android.util.Log
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONArray
import org.json.JSONObject
import java.io.File


class Connector(context: Context,packages: String) {
    var active=Activation(context,packages)
    var API="http://192.168.8.103:3000/"
    var context=context
    var packages=packages
    var TOKEN=""
    fun get_data(callback:(data:Any)->Unit){

    AndroidNetworking.get(API+"data").addHeaders("content-type","Application/json").build().getAsJSONArray(object : JSONArrayRequestListener{
        override fun onResponse(response: JSONArray?) {
         callback(response.toString())
        }

        override fun onError(anError: ANError?) {
        Toast.makeText(context,anError.toString(),Toast.LENGTH_LONG).show()
        }

    })
    }
    fun get_data_details(id:String,callback:(data:Any)->Unit){

        AndroidNetworking.get(API+"details/"+id).addHeaders("content-type","Application/json").build().getAsJSONObject(object :
            JSONObjectRequestListener {
            override fun onResponse(response: JSONObject?) {

                callback(response.toString())
            }

            override fun onError(anError: ANError?) {
                Toast.makeText(context,anError.toString(),Toast.LENGTH_LONG).show()
            }

        })

    }
    fun get_comments(callback:(data:Any)->Unit){




    }
    fun update_image(base64: String,token: String,callback:(data:Array<*>)->Unit){
        var data=JSONObject()
        data.put("token",token)
        data.put("image",base64)
        AndroidNetworking.put(API+"put/image").addHeaders("content-type","Application/json").addJSONObjectBody(data).build().getAsJSONObject(object :
            JSONObjectRequestListener {
            override fun onResponse(response: JSONObject?) {
                var res=JSONObject(response.toString())
                if(res["state"] as Boolean){
                    callback(arrayOf(true,res["msg"]))
                }else{
                    callback(arrayOf(false,res["msg"]))
                }

            }

            override fun onError(anError: ANError?) {
                Toast.makeText(context,anError.toString(),Toast.LENGTH_LONG).show()
            }

        })



    }
    fun get_user_info(callback:(data:Any)->Unit){
        var path= Environment.getExternalStorageDirectory().toString()+"/Android/data/${packages}/.config"
        var token= File(path).readText()
      Check(token){




      }

    }


    fun login(email: String,password: String,callback:(data:Array<Any>)->Unit){
        var data=JSONObject()
        data.put("email",email)
        data.put("password",password)
        AndroidNetworking.post(API+"login/").addHeaders("content-type","Application/json").addJSONObjectBody(data).build().getAsJSONObject(object :
            JSONObjectRequestListener {
            override fun onResponse(response: JSONObject?) {
                var res=JSONObject(response.toString())
                if(res["state"] as Boolean){
                    active.SaveToken(res["token"].toString())
                    callback(arrayOf(true,res["msg"]))
                }else{
                    callback(arrayOf(false,res["msg"]))
                }

            }

            override fun onError(anError: ANError?) {
                Toast.makeText(context,anError.toString(),Toast.LENGTH_LONG).show()
            }

        })


    }
    fun register(user_name:String,email:String,password:String,callback:(data:Array<Any>)->Unit){
        var data=JSONObject()
        data.put("user_name",user_name)
        data.put("email",email)
        data.put("password",password)
        AndroidNetworking.post(API+"register/").addHeaders("content-type","Application/json").addJSONObjectBody(data).build().getAsJSONObject(object :
            JSONObjectRequestListener {
            override fun onResponse(response: JSONObject?) {
                var res=JSONObject(response.toString())
                if(res["state"] as Boolean){
                    active.SaveToken(res["token"].toString())
                    TOKEN=res["token"].toString()
                    callback(arrayOf(true,res["msg"]))
                }else{
                    callback(arrayOf(false,res["msg"]))
                }

            }

            override fun onError(anError: ANError?) {
                Toast.makeText(context,anError.toString(),Toast.LENGTH_LONG).show()
            }

        })
    }

    fun Check(token: String,callback: (data: Array<Any>) -> Unit){
    var data=JSONObject()
        data.put("token",token)

        AndroidNetworking.post(API+"check/").addHeaders("content-type","Application/json").addJSONObjectBody(data).build().getAsJSONObject(object :
            JSONObjectRequestListener {
            override fun onResponse(response: JSONObject?) {
                Log.i("TTTTTTTTTT",response.toString())
                var result=JSONObject(response.toString())["state"] as Boolean
                var msg=JSONObject(response.toString())["msg"] as String
              if(result){
                  var data=JSONObject(response.toString())["data"] as JSONObject
                  callback(arrayOf(result,msg,data))
              }else{
                  callback(arrayOf(result,msg))
              }

            }

            override fun onError(anError: ANError?) {
                Toast.makeText(context,anError.toString(),Toast.LENGTH_LONG).show()
            }

        })
    }
    fun update_password(old_password: String,new_password: String,token: String,callback:(data:Array<Any>)->Unit){
        var data=JSONObject()
        data.put("old_password",old_password)
        data.put("new_password",new_password)
        data.put("token",token)
        AndroidNetworking.put(API+"put/pass").addHeaders("content-type","Application/json").addJSONObjectBody(data).build().getAsJSONObject(object :
            JSONObjectRequestListener {
            override fun onResponse(response: JSONObject?) {
                var res=JSONObject(response.toString())
                if(res["state"] as Boolean){
                    callback(arrayOf(true,res["msg"]))
                }else{
                    callback(arrayOf(false,res["msg"]))
                }

            }

            override fun onError(anError: ANError?) {
                Toast.makeText(context,anError.toString(),Toast.LENGTH_LONG).show()
            }

        })


    }
    fun update_bio(bio: String,token: String,callback:(data:Array<Any>)->Unit){
        var data=JSONObject()
        data.put("bio",bio)
        data.put("token",token)
        AndroidNetworking.put(API+"put/bio").addHeaders("content-type","Application/json").addJSONObjectBody(data).build().getAsJSONObject(object :
            JSONObjectRequestListener {
            override fun onResponse(response: JSONObject?) {
                var res=JSONObject(response.toString())
                if(res["state"] as Boolean){
                    callback(arrayOf(true,res["msg"]))
                }else{
                    callback(arrayOf(false,res["msg"]))
                }

            }

            override fun onError(anError: ANError?) {
                Toast.makeText(context,anError.toString(),Toast.LENGTH_LONG).show()
            }

        })


    }
}
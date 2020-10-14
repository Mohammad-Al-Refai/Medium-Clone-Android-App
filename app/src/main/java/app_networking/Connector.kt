package app_networking

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONArray
import org.json.JSONObject

class Connector(context: Context) {
    var API="http://192.168.8.105:3000/"
    var context=context
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
        Log.i("SSSSSSSS",API+"details/"+id)
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
    fun get_user_info(callback:(data:Any)->Unit){


    }


    fun login(callback:(data:Any)->Unit){




    }
    fun register(block:(data:Any)->Unit){




    }
}
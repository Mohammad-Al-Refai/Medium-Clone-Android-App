package app_networking

import android.content.Context
import android.os.Environment
import android.util.Log
import org.json.JSONObject
import java.io.File
import java.nio.charset.Charset

class Activation(context: Context, packageName: String) {
    var context=context
var package_name=packageName
   fun IsLogging(callback:(Array<Any>)->Unit){
       var path= Environment.getExternalStorageDirectory().toString()+"/Android/data/${this.package_name}/.config"
       var file=File(path)

       if(file.exists()){

           var token= file.readText()
           var connector=Connector(context,package_name)
           connector.Check(token){
               callback(it)
           }
       }else{
           callback(arrayOf(false))
       }

   }
    fun SaveToken(token:String){

        var path= Environment.getExternalStorageDirectory().toString()+"/Android/data/${this.package_name}/.config"
        var file=File(path)
        file.writeText(token, Charset.defaultCharset())
    }
fun getToken(callback:(Array<Any>)->Unit){
    var path= Environment.getExternalStorageDirectory().toString()+"/Android/data/${this.package_name}/.config"
    var file=File(path)

    if(file.exists()){
        var token= file.readText()
            callback(arrayOf(true,token))

    }else{
        callback(arrayOf(false))
    }
}
}
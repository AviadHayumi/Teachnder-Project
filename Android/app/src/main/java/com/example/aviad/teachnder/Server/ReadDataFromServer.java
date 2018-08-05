package com.example.aviad.teachnder.Server;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.example.aviad.teachnder.Card.Card;
import com.example.aviad.teachnder.Chats.ChatObject;
import com.example.aviad.teachnder.Matches.MatchesObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReadDataFromServer {
    static Context context;
    static SharedPreferences sharedPreferences;

    public interface IResult {
        void getResult(boolean isSucceed);
    }

    public interface IResultMatches {
        void getResult(List<MatchesObject> isSucceed);
    }

    public interface IResultChatMessages {
        void getResult(List<ChatObject> isSucceed);
    }



    public interface IResultChat {
        void getResult(boolean isSucceed,String chatId);
    }

    public interface IResultList {
        void getResult(ArrayList<Card> isSucceed);
    }

    public interface IResultListString {
        void getResult(ArrayList<String> isSucceed);
    }

    public interface IResulUser {
        void getResult(Card isSucceed);
    }



    public ReadDataFromServer(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("myRef", Context.MODE_PRIVATE);

    }

    public static void login(final String email, final String password, final IResult listener) {

        GetDataFromServer getDataFromServer = new GetDataFromServer(new GetDataFromServer.iGetServerResult() {
            @Override
            public void onResult(String res) {
                try {

                    JSONObject jsonObject = (JSONObject) new JSONObject(res);
                    String s =jsonObject.get("message").toString();
                    if (!jsonObject.get("message").toString().equals("error")) {

                        String type = jsonObject.getJSONObject("userContent").getString("type").toString();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", email);
                        editor.putString("type",type);
                        editor.commit();

                        if(listener != null){
                            listener.getResult(true);
                        }
                    }
                    else
                    {
                        if(listener != null){
                            listener.getResult(false);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        });
        getDataFromServer.execute(ServerHelper.URL + "/Login/" + email + "/" + password).toString();

    }

    public static void register(final String email, final String password,final String name , final String type, final IResult listener) {

        GetDataFromServer getDataFromServer = new GetDataFromServer(new GetDataFromServer.iGetServerResult() {
            @Override
            public void onResult(String res) {
                try {

                    JSONObject jsonObject = (JSONObject) new JSONObject(res);
                    if (!jsonObject.get("message").toString().equals("error")) {

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", email);
                        editor.putString("type", type);
                        editor.commit();

                        if(listener != null){
                            listener.getResult(true);
                        }
                    }
                    else
                    {
                        if(listener != null){
                            listener.getResult(false);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        });
        getDataFromServer.execute(ServerHelper.URL + "/Register/" + email + "/" + password + "/" + name + "/" + type).toString();

    }

    public static void addMoreInformation(final String name , final String phone,final String about, final IResult listener) {


        final String email = sharedPreferences.getString("email","none").toString();
        GetDataFromServer getDataFromServer = new GetDataFromServer(new GetDataFromServer.iGetServerResult() {
            @Override
            public void onResult(String res) {
                try {

                    JSONObject jsonObject = (JSONObject) new JSONObject(res);
                    if (!jsonObject.get("message").toString().equals("error")) {


                        if(listener != null){
                            listener.getResult(true);
                        }
                    }
                    else
                    {
                        if(listener != null){
                            listener.getResult(false);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        });
        if(!email.equals("none")) {
            getDataFromServer.execute(ServerHelper.URL + "/moreInfo/" + email + "/" + name + "/" + phone + "/" + about).toString();
        }

    }

    public static boolean logout() {

        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", "none");
            editor.putString("type", "none");
            editor.commit();
            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }




    public static void getClient(final IResultList listener) {


        GetDataFromServer getDataFromServer = new GetDataFromServer(new GetDataFromServer.iGetServerResult() {
        ArrayList<Card> resArrayList = new ArrayList<>();

            @Override
            public void onResult(String res) {

                try {


                    JSONObject jsonObject = new JSONObject(res);

                    if (jsonObject.get("message").toString().equals("sucessfully")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("usersContent");
                    for (int i = 0; i < jsonArray.length(); i++) {


                        JSONObject obj = jsonArray.getJSONObject(i);

                        String id,email,name,phone,type,imgUrl,about;
                        id=null; email=null; phone=null; imgUrl=null; about=null; name=null; type=null;

                        if(obj.getString("_id")!=null) {
                            id = obj.getString("_id");
                        }

                        if(obj.getString("email")!=null) {
                            email = obj.getString("email");
                        }

                        if(obj.getString("name")!=null) {
                            name = obj.getString("name");
                        }

                        if(obj.getString("phone")!=null) {
                            phone = obj.getString("phone");
                        }


                        if(obj.getString("type")!=null) {
                            type = obj.getString("type");
                        }

                        if(obj.getString("about")!=null) {
                            about = obj.getString("about");
                        }

                        resArrayList.add(new Card(id, email, name, "default", about, phone, type));
                    }

                        if(listener!=null) {
                            listener.getResult(resArrayList);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        getDataFromServer.execute(ServerHelper.URL + "/find20client/").toString();


    }

    public static void getUserMatches(final IResultMatches listener) {


        GetDataFromServer getDataFromServer = new GetDataFromServer(new GetDataFromServer.iGetServerResult() {
            ArrayList<MatchesObject> resArrayList = new ArrayList<>();

            @Override
            public void onResult(String res) {

                try {


                    JSONObject jsonObject = new JSONObject(res);

                    if (jsonObject.get("message").toString().equals("sucessfully")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("userEmail");
                        for (int i = 0; i < jsonArray.length(); i++) {


                            JSONObject obj = jsonArray.getJSONObject(i);

                            String id,email,imgUrl;

                            id=null;
                            email=null;

                            if(obj.getString("matchid")!=null) {
                                id = obj.getString("matchid");
                            }

                            if(obj.getString("useremail")!=null) {
                                email = obj.getString("useremail");
                            }

                            resArrayList.add(new MatchesObject(id, email, "default"));
                        }

                        if(listener!=null) {
                            listener.getResult(resArrayList);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        getDataFromServer.execute(ServerHelper.URL + "/GetUserMatches/"+sharedPreferences.getString("email" , "none")).toString();

        }


    public static void getBusinessOwners(final IResultList listener) {


        GetDataFromServer getDataFromServer = new GetDataFromServer(new GetDataFromServer.iGetServerResult() {
            ArrayList<Card> resArrayList = new ArrayList<>();

            @Override
            public void onResult(String res) {

                try {


                    JSONObject jsonObject = new JSONObject(res);

                    if (jsonObject.get("message").toString().equals("sucessfully")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("usersContent");
                        for (int i = 0; i < jsonArray.length(); i++) {


                            JSONObject obj = jsonArray.getJSONObject(i);

                            String id,email,name,phone,type,imgUrl,about;
                            id=null; email=null; phone=null; imgUrl=null; about=null; name=null; type=null;

                            if(obj.getString("_id")!=null) {
                                id = obj.getString("_id");
                            }

                            if(obj.getString("email")!=null) {
                                email = obj.getString("email");
                            }

                            if(obj.getString("name")!=null) {
                                name = obj.getString("name");
                            }

                            if(obj.getString("phone")!=null) {
                                phone = obj.getString("phone");
                            }


                            if(obj.getString("type")!=null) {
                                type = obj.getString("type");
                            }

                            if(obj.getString("about")!=null) {
                                about = obj.getString("about");
                            }

                            resArrayList.add(new Card(id, email, name, "default", about, phone, type));
                        }

                        if(listener!=null) {
                            listener.getResult(resArrayList);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        getDataFromServer.execute(ServerHelper.URL + "/find20businessOwner").toString();

    }



    public static void setNope(String email1,String email2, final IResult listener) {

        GetDataFromServer getDataFromServer = new GetDataFromServer(new GetDataFromServer.iGetServerResult() {
            @Override
            public void onResult(String res) {

                try {

                    JSONObject jsonObject = (JSONObject) new JSONObject(res);
                    String s =jsonObject.get("message").toString();
                    if (jsonObject.get("message").toString().equals("sucessfully")) {


                        if(listener != null){
                            listener.getResult(true);
                        }
                    }
                    else
                    {
                        if(listener != null){
                            listener.getResult(false);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }
        });
        getDataFromServer.execute(ServerHelper.URL + "/SetNope/" + email1 +"/" + email2).toString();


    }

    public static void setYep(String email1,String email2, final IResult listener) {

        GetDataFromServer getDataFromServer = new GetDataFromServer(new GetDataFromServer.iGetServerResult() {
            @Override
            public void onResult(String res) {

                try {

                    JSONObject jsonObject = (JSONObject) new JSONObject(res);
                    String s =jsonObject.get("message").toString();
                    if (jsonObject.get("message").toString().equals("sucessfully")) {


                        if(listener != null){
                            listener.getResult(true);
                        }
                    }
                    else
                    {
                        if(listener != null){
                            listener.getResult(false);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }
        });
        getDataFromServer.execute(ServerHelper.URL + "/SetYep/" + email1 +"/" + email2).toString();


    }


    public static void setMatch(String email1,String email2,String chatID, final IResult listener) {

        GetDataFromServer getDataFromServer = new GetDataFromServer(new GetDataFromServer.iGetServerResult() {
            @Override
            public void onResult(String res) {
                try {

                    JSONObject jsonObject = (JSONObject) new JSONObject(res);
                    if (jsonObject.get("message").toString().equals("sucessfully")) {


                        if(listener != null){
                            listener.getResult(true);
                        }
                    }
                    else
                    {
                        if(listener != null){
                            listener.getResult(false);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }
        });
        getDataFromServer.execute(ServerHelper.URL + "/SetMatch/" + email1 +"/" + email2+ "/" + chatID).toString();


    }


    public static void getUserByEmail(String email , final IResulUser listener) {


        GetDataFromServer getDataFromServer = new GetDataFromServer(new GetDataFromServer.iGetServerResult() {

            @Override
            public void onResult(String res) {

                Card card = null;

                try {

                    JSONObject jsonObject1 = new JSONObject(res);

                    if (jsonObject1.get("message").toString().equals("sucessfully")) {

                        JSONObject jsonObject = (new JSONObject(res) ).getJSONObject("userContent");

                        String id = jsonObject.getString("_id");
                        String email1 = jsonObject.getString("email");
                        String name = jsonObject.getString("name");
                        String phone = jsonObject.getString("phone");
                        String type = jsonObject.getString("type");
                        String about1 = jsonObject.getString("about");

                        card = new Card(id, email1, name, null, about1, phone, type);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if(listener!=null) {
                        listener.getResult(card);
                    }
                }
            }
        });

        getDataFromServer.execute(ServerHelper.URL + "/GetUserByEmail/" + email).toString();




    }


    public static void createChat(String email1,String email2, final IResultChat listener) {

        GetDataFromServer getDataFromServer = new GetDataFromServer(new GetDataFromServer.iGetServerResult() {
            @Override
            public void onResult(String res) {

                try {


                    JSONObject jsonObject = (JSONObject) new JSONObject(res);
                    if (jsonObject.get("message").toString().equals("successfully")) {

                        JSONObject jsonObject1 = jsonObject.getJSONObject("content");
                        String id =jsonObject1.get("_id").toString();

                        if(listener != null){
                            listener.getResult(true,id);
                        }
                    }
                    else
                    {
                        if(listener != null){
                            listener.getResult(false,null);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }
        });
        getDataFromServer.execute(ServerHelper.URL + "/CreateChat/" + email1 +"/" + email2).toString();


    }

    public static void sendMessage(String content,String chatId, final IResult listener) {


        String name = sharedPreferences.getString("email","none");
        GetDataFromServer getDataFromServer = new GetDataFromServer(new GetDataFromServer.iGetServerResult() {
            @Override
            public void onResult(String res) {

                try {

                    JSONObject jsonObject = (JSONObject) new JSONObject(res);

                    if (jsonObject.get("message").toString().equals("sucessfully")) {


                        if(listener != null){
                            listener.getResult(true);
                        }
                    }
                    else
                    {
                        if(listener != null){
                            listener.getResult(false);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        });
        getDataFromServer.execute(ServerHelper.URL + "/AddMessageToChat/" + chatId +"/" + name+ "/" +content).toString();


    }

    public static void getMessages(String chatId, final IResultChatMessages listener) {

        GetDataFromServer getDataFromServer = new GetDataFromServer(new GetDataFromServer.iGetServerResult() {
            @Override
            public void onResult(String res) {

                ArrayList<ChatObject> resList = new ArrayList<>();

                try {

                    JSONObject jsonObject = (JSONObject) new JSONObject(res);
                    if (jsonObject.get("message").toString().equals("sucessfully")) {

                        JSONArray jsonArray = jsonObject.getJSONObject("chatContent").getJSONArray("messages");

                        for (int i=0; i<jsonArray.length(); i++ ) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String user = jsonObject1.getString("useremail").toString();
                            String content = jsonObject1.getString("content").toString();
                            boolean isCurrentUser = user.equals(sharedPreferences.getString("email","none"));

                            resList.add(new ChatObject(content,isCurrentUser));

                        }


                        if(listener != null){
                            listener.getResult(resList);
                        }
                    }
                    else
                    {
                        if(listener != null){
                            listener.getResult(null);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        });
        getDataFromServer.execute(ServerHelper.URL + "/ShowChatMessages/" + chatId).toString();


    }


    public static void isContainYep(final String myUserEmail, String otherUserEmail , final IResult listener) {


        GetDataFromServer getDataFromServer = new GetDataFromServer(new GetDataFromServer.iGetServerResult() {
            ArrayList<String> resArrayList = new ArrayList<>();
            boolean result = false;

            @Override
            public void onResult(String res) {
                try {
                    result = false;
                    JSONObject jsonObject = new JSONObject(res);

                    if (jsonObject.get("message").toString().equals("sucessfully")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("userContent");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject obj = jsonArray.getJSONObject(i);
                            resArrayList.add(obj.getString("useremail").toString());

                            if(obj.getString("useremail").toString().equals(myUserEmail)) {
                                result = true;
                            }
                        }
                    }

                    if(listener!=null) {
                        listener.getResult(result);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        getDataFromServer.execute(ServerHelper.URL + "/GetUserYeps/" + otherUserEmail).toString();

    }

    public static void isContainNope(final String myUserEmail, String otherUserEmail , final IResult listener) {


        GetDataFromServer getDataFromServer = new GetDataFromServer(new GetDataFromServer.iGetServerResult() {
            ArrayList<String> resArrayList = new ArrayList<>();
            boolean result = false;

            @Override
            public void onResult(String res) {
                try {
                    result = false;
                    JSONObject jsonObject = new JSONObject(res);

                    if (jsonObject.get("message").toString().equals("sucessfully")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("userContent");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject obj = jsonArray.getJSONObject(i);
                            resArrayList.add(obj.getString("useremail").toString());

                            if(obj.getString("useremail").toString().equals(myUserEmail)) {
                                result = true;
                            }
                        }

                        if(listener!=null) {
                            listener.getResult(result);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        getDataFromServer.execute(ServerHelper.URL + "/GetUserNopes/" + otherUserEmail).toString();

    }


    public static void findWithAbout(String about, final IResultList listener) {

        GetDataFromServer getDataFromServer = new GetDataFromServer(new GetDataFromServer.iGetServerResult() {
            ArrayList<Card> resArrayList = new ArrayList<>();

            @Override
            public void onResult(String res) {

                try {


                    JSONObject jsonObject = new JSONObject(res);

                    if (jsonObject.get("message").toString().equals("sucessfully")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("usersContent");
                        for (int i = 0; i < jsonArray.length(); i++) {


                            JSONObject obj = jsonArray.getJSONObject(i);

                            String id,email,name,phone,type,imgUrl,about;
                            id=null; email=null; phone=null; imgUrl=null; about=null; name=null; type=null;

                            if(obj.getString("_id")!=null) {
                                id = obj.getString("_id");
                            }

                            if(obj.getString("email")!=null) {
                                email = obj.getString("email");
                            }

                            if(obj.getString("name")!=null) {
                                name = obj.getString("name");
                            }

                            if(obj.getString("phone")!=null) {
                                phone = obj.getString("phone");
                            }


                            if(obj.getString("type")!=null) {
                                type = obj.getString("type");
                            }

                            if(obj.getString("about")!=null) {
                                about = obj.getString("about");
                            }

                            resArrayList.add(new Card(id, email, name, "default", about, phone, type));
                        }

                        if(listener!=null) {
                            listener.getResult(resArrayList);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        getDataFromServer.execute(ServerHelper.URL + "/findWithAbout/" + about).toString();

    }



}



    //TODO: login => v
    //TODO : register => v
    //TODO : add more information => V

    //TODO : get user by email => V
    //TODO : get 20 clients => V
    //TODO : get 20 business owner => V



    //TODO : Check Match => V

    //TODO : Set Nope => V
    //TODO : Set Yep = > V
    //TODO : Set Match => V


    //TODO : find with about
    //TODO: Send message => V
    //TODO : Get messages => V



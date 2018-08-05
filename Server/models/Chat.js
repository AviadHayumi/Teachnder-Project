const mongoose = require('mongoose');
const Schema = mongoose.Schema;

//Create Schema
const ChatSchema = new Schema({
    user1 : String ,
    user2 : String, 
    messages :{ type: [message] }
});

var message = new mongoose.Schema({
    useremail: String,
    content : String , 
    created: {type: Date, default:Date.now}
});


mongoose.model('chats' , ChatSchema);
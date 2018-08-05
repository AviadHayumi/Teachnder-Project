const mongoose = require('mongoose');
const Schema = mongoose.Schema;

//Create Schema
const UserSchema = new Schema({
    
  email: {
    type:String,
    required:true
  } , 
  password: {
    type:String,
    required:true
  } , 
  name:{
    type: String , 
  },
  phone:{
    type: String , 
  },
  type:{
    type: String , 
  },
  about:{
    type: String , 
  },
  imgUrl:{
    type: String , 
  },
  yeps :{ type: [YepSchema] },
  nopes :{ type: [NopeSchema] },
  matches :{ type: [MatcheSchema] },
  date : {
    type:Date,
    default: Date.now
  }
});

var YepSchema = new mongoose.Schema({
    useremail: String,
    created: {type: Date, default:Date.now}
});

var NopeSchema = new mongoose.Schema({
    useremail: String,
    created: {type: Date, default:Date.now}
});

var MatcheSchema = new mongoose.Schema({
    useremail: String,
    matchid: String,
    created: {type: Date, default:Date.now}
});



mongoose.model('users' , UserSchema);
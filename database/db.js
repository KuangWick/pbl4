

const mongoose = require("mongoose");
require("dotenv").config({ path: "../config/.env"});


const connectDB = async () => {
    try {
      await mongoose.connect(
        'mongodb+srv://'+process.env.MONGO_USER+':'+process.env.MONGO_PASSWORD+'@cluster0.qeor9ui.mongodb.net/?retryWrites=true&w=majority',
      );
      console.log("Connection To kuangWIck DataBase Succeeded.");
    } catch (err) {
      console.log("Failed" +err);
    }
  };
  
  module.exports = {
    connectDB,
  };



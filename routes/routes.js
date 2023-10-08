
const express = require('express');
const router = express.Router();




const Usercontroller = require("../controller/userController");
router.get("/Users", Usercontroller.getAllUsers);
router.get("/SingleUser/:id", Usercontroller.getSingleUser);
router.post("/NewUser", Usercontroller.NewUser);
router.delete("/DeleteUser/:id", Usercontroller.DeleteUser);
router.patch("/UpdateUser/:id", Usercontroller.UpdateUser);
router.post("/UserLogIn", Usercontroller.UserLogIn);


module.exports = router;
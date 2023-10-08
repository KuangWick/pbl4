const mongoose = require("mongoose");

const classSchema = new mongoose.Schema({
    MaLop: {
        type: String,
        ref: 'Student',
        required: true,
        unique: true,
    },
    TenLop: {
        type: String,
        required: true,
    },
    MaKhoa: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Faculty',
        required: true,
    },
});

const Class = mongoose.model("Class", classSchema);

module.exports = Class;

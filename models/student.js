const mongoose = require('mongoose');


const studentSchema = new mongoose.Schema({
    MaSv: {
        type: String,
        required: true,
        unique: true,
    },
    HoTenSv: {
        type: String,
        required: true
    },
    MaLop: {
        type: String,
        ref: 'Class',
        required: true
    },
    DiaChi: {
        type: String,
        required: true,
    },
    Ngaysinh: {
        type: Date,
        required: true
    },
    Gioitinh: {
        type: String,
        required: true
    },
    Email: {
        type: String,
        required: true,
        unique: true
    },
    BangDiem: [
        {
            type: mongoose.Schema.Types.ObjectId,
            ref: 'Score'
        },
    ] ,
    createdAt: {
        type: Date,
        default: Date.now
    },
    updatedAt: {
        type: Date,
        default: Date.now
    },
})
module.exports = mongoose.model('Student', studentSchema);

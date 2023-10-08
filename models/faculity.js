const mongoose = require('mongoose');

const facultySchema = new mongoose.Schema({
    MaKhoa: {
        type: String,
        ref: 'Class',
        required: true,
        unique: true,
    },
    TenKhoa: {
        type: String,
        required: true,
    },
});

const Faculty = mongoose.model('Faculty', facultySchema);

module.exports = Faculty;

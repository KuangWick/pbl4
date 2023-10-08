const mongoose = require('mongoose');

const scoreSchema = new mongoose.Schema({
MaSv: {
    type: String,
    ref: 'Student',
    required: true,
},
MaMh: {
    type: String,
    ref: 'Subject',
    required: true,
},
MaLop: {
    type: String,
    ref: 'Class',
    required: true,
},
DiemBt: {
    type: Number,
    min: 0,
    max: 10,
},
DiemGK: {
    type: Number,
    min: 0,
    max: 10,
},
DiemCK: {
    type: Number,
    min: 0,
    max: 10,
},
DiemTK: {
    type: Number,
    min: 0,
    max: 10,
},
});

const Score = mongoose.model('Score', scoreSchema);

module.exports = Score;
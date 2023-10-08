const mongoose = require("mongoose");

const subjectSchema = new mongoose.Schema({
  MaMh: {
    type: String,
    ref: 'Score',
    required: true,
    unique: true,
  },
  TenMh: {
    type: String,
    required: true,
  },
  SoTinChi: {
    type: Number,
    required: true,
  },
});

const Subject = mongoose.model("Subject", subjectSchema);

module.exports = Subject;

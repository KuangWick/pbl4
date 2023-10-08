const nodemailer = require("nodemailer");
require("dotenv").config({ path: "../config/.env" });



const EmailMessage = async (UserEmail) => {
    const Transporter = nodemailer.createTransport({
        service: "gmail",
        port: 465,
        secure: true,
        logger: false,
        debug: false,
        secureConnection: false,
        auth: {
            user: process.env.EMAIL_USER,
            pass: process.env.EMAIL_USER_PASSWORD,
        },
        tls: {
            rejectUnauthorized: true,
        },
    });
    const Option = {
        from: 'Kuang:'+ process.env.EMAIL_USER +'',
        to: UserEmail,
        subject: "Account Creation",
        text: "Your account has been Successfully Created",
        html: `<font color="black">
        <center>
            <h2> Congratulations Your account has been Successfully Created </h2><br>
        </center>
  </font>`,
    };

    Transporter.sendMail(Option, (err) => {
        if(!err) {
            console.log('Mail has been Send to '+UserEmail+'.')
            Transporter.close();
        } else {
            console.log('Mail sending Failed...!');
            Transporter.close();
        }
    });
};
module.exports = { EmailMessage };

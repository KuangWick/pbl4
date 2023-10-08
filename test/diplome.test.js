let Server = require("../index");
let chai = require("chai");
let chaiHttp = require("chai-http");
chai.should();
chai.use(chaiHttp);

/**
 * Test cases : to test all the Diplome APIs
 * Covered Routes:
 * (1) Get all Diplomes
 * (2) Store Diplome
 * (3) Get single Diplome
 * (4) Fail to Get a single Diplome
 * (5) Update Diplome
 * (6) Fail to Delete a Diplome
 * (7) Delete Diplome
 * (8) Fail to Update a Diplome
 */

describe("Testing Diplome APIs", () => {
  /**
   * Testing Get all API.
   */

  describe("Get all", () => {
    it("It Should Get all the Diplomes ", () => {
      chai
        .request(Server)
        .get("/Api/Diplomes")
        .end((err, res) => {
          res.should.have.status(200);
          res.body.should.have.property("message").eql("Operation Succeded");
        });
    });
  });
});

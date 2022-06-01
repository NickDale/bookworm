using BookWorm.Models;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Linq;

namespace BookWorm.Repository.Tests
{
    [TestClass()]
    public class UserRepositoryTests
    {

        [TestMethod()]
        public void GetAllLibrarianTest()
        {
            UserRepository userRepository = new UserRepository();
            var librarians = userRepository.GetAllLibrarian();

            Assert.IsNotNull(librarians,"A list nem lehet null!");
            Assert.IsTrue(librarians.Count > 0, "A list nem lehet üres!");
        }

        [TestMethod()]
        public void InsertUserTest()
        {
            UserRepository userRepository = new UserRepository();

            int numberOfusersInDb = userRepository.CountUser();

            users user = new users();
            user.id = numberOfusersInDb + 1;
            user.firstname = "Elek";
            user.lastname = "Test";
            user.email = "test@gmail.com";
            user.username = "test_elek";
            user.user_type_id = 3;
            user.created_date = DateTime.Now;
            user.created_by = "test";

            userRepository.Insert(user);
            
            var newCount = userRepository.CountUser();
            Assert.IsTrue(numberOfusersInDb < newCount, "Nem sikerült a beszúrás");

            var users = userRepository.GetAllSimpleUser();
            var userList = users.Where(u => u.email.Equals(user.email)).ToList();

            Assert.IsFalse(userList.Count == 0, "Nem szerepel ilyen email címmel user a db-ben");
            Assert.IsTrue(userList[0].username.Equals(user.username), "Nem ugyanaz a usernem");

        }

    }
}
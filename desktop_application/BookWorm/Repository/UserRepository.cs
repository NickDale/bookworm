using BookWorm.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows;

namespace BookWorm.Repository
{
    public class UserRepository
    {
        
        private bookwormContext db;
        public UserRepository()
        {
            db = new bookwormContext();  
        }

        public users LoginUser(string username)
        {
            users user = db.users.FirstOrDefault(o => o.username == username);
            return user;
        }

        public users UserSearch (string qrCode)
        {
            var user = db.users.FirstOrDefault(u => u.QR_code.Equals(qrCode));
            return user;
        }

        public void Insert(users user)
        {
            try
            {
                db.users.Add(user);
                db.SaveChanges();
            }catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            
        }

        public int CountUser()
        {
            return db.users.Count();
        }

        public List<users> GetAllAdmin()
        {
            return db.users.Where(u => u.user_type_id.Equals(1)).ToList();
        }
        public List<users> GetAllLibrarian()
        {
            return db.users.Where(u => u.user_type_id.Equals(2)).ToList();
        }

        public List<users> GetAllSimpleUser()
        {
            return db.users.Where(u => u.user_type_id.Equals(3)).ToList();
        }

        public void deactivateUser(int id)
        {
            var user = db.users.FirstOrDefault(u => u.id == id);
            if (user.active == true)
            {
                user.active = false;
            }
            db.users.Update(user);
            db.SaveChanges();

        }

        public void activateUser(int id)
        {
            var user = db.users.FirstOrDefault(u => u.id == id);
            
            if ( user.active == false)
            {
                user.active = true;
            }
                db.users.Update(user);
                db.SaveChanges();
           

        }
        public List<user_type> GetAllUserType()
        {
            return db.user_type.ToList();
        }

        
    }
}

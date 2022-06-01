using BookWorm.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BookWorm.Repository
{
    
    class AuthorRepository
    {
        private bookwormContext db;
        public AuthorRepository()
        {
            db = new bookwormContext();
        }

        public List<author> allAuthors()
        {



            return db.author.OrderByDescending(x => x.name).ToList();
        }
        public author searchById(int authId)
        {
            var oneAuthor = db.author.FirstOrDefault(o => o.id == authId);

            return oneAuthor;
        }
        public void updateAuthor (author author)
        {
           
                db.author.Update(author);
                db.SaveChanges();
          
        }
        public void InsertAuthor(author author)
        {
          
                db.author.Add(author);

        }
        
    }
}

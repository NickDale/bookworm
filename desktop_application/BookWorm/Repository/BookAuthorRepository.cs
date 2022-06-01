using BookWorm.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BookWorm.Repository
{
    class BookAuthorRepository
    {
        private bookwormContext db;

        public BookAuthorRepository()
        {
            db = new bookwormContext();
        }

        public void InsertToBookAuthor(book_author book_Author)
        {
                db.book_author.Add(book_Author);
                db.SaveChanges();
        }
    }
}
    
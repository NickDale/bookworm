using BookWorm.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Media.Imaging;

namespace BookWorm.Repository
{
    class BookRepository
    {
        private bookwormContext db;
        public BookRepository()
        {
            db = new bookwormContext();
        }

        public int getLastBookId()
        {
            var book = db.book.OrderBy(book => book.id).Last();
            int lastBookId = book.id + 1;

            return lastBookId;
        }

        public book bookSearch(string barcode)
        {
            var book = db.book.FirstOrDefault(b => b.barcode == barcode);
            
            return book;
        }
        public void bookInsert(book book)
        {
            try
            {
                db.book.Add(book);
                db.SaveChanges();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }


        public List<category> FindAllCategory()
        {
            var category = db.category.ToList();

            return category;
        }
         
}
}

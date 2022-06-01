using BookWorm.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BookWorm.Repository
{
    class BookLoanRepository
    {
        private bookwormContext db;

        public BookLoanRepository()
        {
            db = new bookwormContext();
        }

        public void InsertToBookLoan(book_loan bookLoan)
        {

                db.book_loan.Add(bookLoan);
                db.SaveChanges();

        }

        public List<book_loan> AllLoanSearch()
        {
            var book_loan = db.book_loan.ToList();
           

            return book_loan;
        }

    }
}

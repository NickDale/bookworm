using BookWorm.Models;
using BookWorm.Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace BookWorm
{
    /// <summary>
    /// Interaction logic for AddBook.xaml
    /// </summary>
    public partial class AddBook : Window
    {
        private AuthorRepository autRepo = new AuthorRepository();
        private BookRepository BookRepository = new BookRepository();
        private BookAuthorRepository bookAutRepo = new BookAuthorRepository();
        public AddBook()
        {
            InitializeComponent();


            List<author> authors = autRepo.allAuthors();
            int listAuthlength = authors.Count();
            for (int i = 0; i < listAuthlength; i++)
            {
                authorsBox.Items.Add(authors[i]);
            }

            List<category> category = BookRepository.FindAllCategory();
            int listCategoryLength = category.Count();
            for (int i = 0; i < listCategoryLength; i++)
            {
                CategoryId.Items.Add(category[i]);
                
            }
            


        }

        private void NavigateToMenu(object sender, RoutedEventArgs e)
        {
            MenuWindow menu = new MenuWindow();
            menu.Show();
            this.Close();
        }

        private void CloseApp(object sender, MouseButtonEventArgs e)
        {
            try
            {
                Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void Minimize(object sender, MouseButtonEventArgs e)
        {
            try
            {
                this.WindowState = WindowState.Minimized;

            }
            catch (Exception ex)
            {

                MessageBox.Show(ex.Message);
            }
        }

        private void InsertBook(object sender, RoutedEventArgs e)
        {
            bool loanCheck = false;

            if ((bool)CheckBoxYes.IsChecked)
            {
                loanCheck = true;
            }

            int categoryID;
            bool result = int.TryParse(CategoryId.SelectedValue.ToString(), out categoryID);

            int authorId;
            bool Aut = int.TryParse(authorsBox.SelectedValue.ToString(), out authorId);

            int bookId = BookRepository.getLastBookId();

            book book = new book()
            {
                title = txtTitle.Text,
                isbn = txtIsbn.Text,
                img_link = ImgLink.Text,
                short_description = ShortDescript.Text,
                loanable = loanCheck,
                max_loan_days = Convert.ToInt32(MaxLoanDay.Text),
                category_id = categoryID,
                barcode = BarcodeInput.Text,
                created_date = DateTime.Now,
                created_by = MainWindow.konyvtarosGlobalUsername,
            };
            BookRepository.bookInsert(book);

            book_author bookAut = new book_author()
            {
                author_id = authorId,
                book_id = bookId
            };
            bookAutRepo.InsertToBookAuthor(bookAut);

            MenuWindow menu = new MenuWindow();
            menu.Show();
            this.Close();
        }
    }
}

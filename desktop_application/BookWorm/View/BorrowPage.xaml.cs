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
    /// Interaction logic for BorrowPage.xaml
    /// </summary>
    public partial class BorrowPage : Window
    {
        private BookRepository bookRepo = new BookRepository();
        private UserRepository userRepo = new UserRepository();
        private BookLoanRepository bookLoanRepo = new BookLoanRepository();
        private List<book> datagridBooks = new List<book>();
        private int userId;

        public BorrowPage()
        {
            InitializeComponent();
  
        }

        private void MenuItem_Click(object sender, RoutedEventArgs e)
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

        private void Enter(object sender, KeyEventArgs e)
        {
            if(e.Key == Key.Return)
            {
                book oneBook = bookRepo.bookSearch(Barcode.Text);
                string hiba = "A könyv jelenleg nem kölcsönözhető";
                bool isLoanable = true;


                if (oneBook.loanable == isLoanable)
                {
                    DataGrid.Items.Add(oneBook);
                    BitmapImage bi3 = new BitmapImage();
                    bi3.BeginInit();
                    bi3.UriSource = new Uri(oneBook.img_link, UriKind.Absolute);
                    bi3.EndInit();
                    imgSRC.Source = bi3;
                    Barcode.Clear();
                    datagridBooks.Add(oneBook);
                }
                else
                {
                    MessageBox.Show(hiba);
                    Barcode.Clear();
                }
                
            }
            
        }


        private void userIdFocused(object sender, MouseButtonEventArgs e)
        {
            QR.SelectAll();
        }

        private void InsertLoan(object sender, RoutedEventArgs e)
        {
            
            foreach (book OneBook in datagridBooks)
            {
                book_loan bookLoan = new book_loan()
                {
                    estimate_end_date = DateTime.Now.AddDays(OneBook.max_loan_days),
                    book_id = OneBook.id,
                    created_date = DateTime.Now,
                    created_by = MainWindow.konyvtarosGlobalUsername,
                    user_id = userId

                };
                bookLoanRepo.InsertToBookLoan(bookLoan);
                

            }
            MessageBox.Show("Sikeres");
            BorrowPage borrow = new BorrowPage();
            borrow.Show();
            this.Close();


        }

        private void qrEnter(object sender, KeyEventArgs e)
        {
            if (e.Key == Key.Return)
            {
               users oneUser = userRepo.UserSearch(QR.Text.ToString());
                if(oneUser != null && oneUser.active != false)
                {
                    userId = oneUser.id;
                    userName.Text = oneUser.firstname + " " + oneUser.lastname;
                    userEmail.Text = oneUser.email;
                    userPhone.Text = oneUser.phone_number;
                    userAddress.Text = oneUser.city + " " + oneUser.street + " " + oneUser.street_number;
                }
                else
                {
                    MessageBox.Show("Nincs ilyen aktív felhasználó az adatbázisban.");
                }
            }
        }

        private void Clear(object sender, MouseButtonEventArgs e)
        {
            QR.SelectAll();
        }
    }
}

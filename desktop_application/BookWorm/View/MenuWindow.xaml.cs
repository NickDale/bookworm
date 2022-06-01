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
    /// Interaction logic for MenuWindow.xaml
    /// </summary>
    public partial class MenuWindow : Window
    {
        
        public MenuWindow()
        {
            InitializeComponent();
        }

        private void Close(object sender, RoutedEventArgs e)
        {
            MainWindow main = new MainWindow();
            main.Show();
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

        private void BookBorrow(object sender, MouseButtonEventArgs e)
        {
            BorrowPage borrow = new BorrowPage();
            borrow.Show();
            this.Close();
        }

        private void NavigateAddUser(object sender, MouseButtonEventArgs e)
        {
            AddUser userAdd = new AddUser();
            userAdd.Show();
            this.Close();
        }

        private void NavigateToAddBook(object sender, MouseButtonEventArgs e)
        {
            
            AddBook userBook = new AddBook();
            userBook.Show();
            this.Close();

        }

        private void NavigateToAuthorAdd(object sender, MouseButtonEventArgs e)
        {
            AddAuthor addAut = new AddAuthor();
            addAut.Show();
            this.Close();
        }

        private void NavigateToListAllBorrow(object sender, MouseButtonEventArgs e)
        {
            AllBorrowList allBorrow = new AllBorrowList();
            allBorrow.Show();
            this.Close();

        }
    }
}

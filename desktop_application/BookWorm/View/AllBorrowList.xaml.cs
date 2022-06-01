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
    /// Interaction logic for AllBorrowList.xaml
    /// </summary>
    public partial class AllBorrowList : Window
    {
        private BookLoanRepository bookLoanRep = new BookLoanRepository();
        public AllBorrowList()
        {
            InitializeComponent();
            var books = bookLoanRep.AllLoanSearch();
            int count = 0;
            foreach (var oneBook in books)
            {
                AllBorrowGrid.Items.Add(oneBook);
                count++;
            }
            AllBorrowNum.Text = count.ToString();


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
    }
}

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
    /// Interaction logic for AddAuthor.xaml
    /// </summary>
    public partial class AddAuthor : Window
    {
        private AuthorRepository autRepo = new AuthorRepository();

        private author selectedAuthor = new author();
        private DateTime createdDate;
        public AddAuthor()
        {
            InitializeComponent();


            

            List<author> authors = autRepo.allAuthors();
            int listAuthlength = authors.Count();
            for (int i = 0; i < listAuthlength; i++)
            {
                authorsBox.Items.Add(authors[i]);
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

        private void AuthorSelected(object sender, SelectionChangedEventArgs e)
        {

            ModiButton.IsEnabled = true;
        }

        private void ModiTheAuthor(object sender, RoutedEventArgs e)
        {
            AuthorName.Visibility = Visibility.Visible;
            AuthorNameBorder.Visibility = Visibility.Visible;
            CreatedBy.Visibility = Visibility.Visible;
            CreatedByBorder.Visibility = Visibility.Visible;

            int AuthorClassID = Int32.Parse(authorsBox.SelectedValue.ToString());

            selectedAuthor = autRepo.searchById(AuthorClassID);
            createdDate = selectedAuthor.created_date;

            AuthorName.Text = selectedAuthor.name;
            CreatedBy.Text = selectedAuthor.created_by;
            UploadAuthor.IsEnabled = true;

        }


        private void UpdateAuthor(object sender, RoutedEventArgs e)
        {
            AuthorName.Visibility = Visibility.Hidden;
            AuthorNameBorder.Visibility = Visibility.Hidden;
            CreatedBy.Visibility = Visibility.Hidden;
            CreatedByBorder.Visibility = Visibility.Hidden;
            ModiButton.IsEnabled = false;

            selectedAuthor.name = AuthorName.Text;
            selectedAuthor.created_by = CreatedBy.Text;
            selectedAuthor.last_mod_user = MainWindow.konyvtarosGlobalUsername;
            selectedAuthor.last_mod_date = DateTime.Now;

            authorsBox.SelectedIndex = -1;

            
            autRepo.updateAuthor(selectedAuthor);

        }

        private void InsertNewAuthor(object sender, RoutedEventArgs e)
        {
            NewAuthor.Visibility = Visibility.Visible;
            NewAuthorBorder.Visibility = Visibility.Visible;
            InsertAuthor.Visibility = Visibility.Visible;
            InsertAuthor.IsEnabled = true;



        }


        private void UploadNewAuthor(object sender, RoutedEventArgs e)
        {
            author author = new author()
            {
                name = NewAuthor.Text,
                created_date = DateTime.Now,
                created_by = MainWindow.konyvtarosGlobalUsername,
            };
            autRepo.InsertAuthor(author);

            NewAuthor.Visibility = Visibility.Hidden;
            NewAuthorBorder.Visibility = Visibility.Hidden;
            InsertAuthor.Visibility = Visibility.Hidden;
            InsertAuthor.IsEnabled = false;

            authorsBox.Items.Add(author);
            NewAuthor.Clear();
        }


    }
}

using BookWorm.Models;
using BookWorm.Repository;
using System;
using System.Collections.Generic;
using System.Data;
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
    /// Interaction logic for AdminPage.xaml
    /// </summary>
    public partial class AdminPage : Window
    {
        private UserRepository userRep = new UserRepository();
        private List<users> Users = new List<users>();
        private users selectedUser = new users();
        public AdminPage()
        {
            InitializeComponent();
            filingAllAdminGridUsingDataTable();
            filingAllLibrarianGridUsingDataTable();
            filingAllNormalUserGridUsingDataTable();



        }
            void filingAllAdminGridUsingDataTable()
        {
            DataTable UserTable = new DataTable();
            DataColumn id = new DataColumn("ID", typeof(int));
            DataColumn firstname = new DataColumn("Firstname", typeof(string));
            DataColumn lastname = new DataColumn("Lastname", typeof(string));
            DataColumn email = new DataColumn("Email", typeof(string));
            DataColumn active = new DataColumn("Active", typeof(Boolean));
            DataColumn phoneNumber = new DataColumn("Phonenumber", typeof(string));
            DataColumn language = new DataColumn("Language", typeof(string));

            UserTable.Columns.Add(id);
            UserTable.Columns.Add(firstname);
            UserTable.Columns.Add(lastname);
            UserTable.Columns.Add(email);
            UserTable.Columns.Add(active);
            UserTable.Columns.Add(phoneNumber);
            UserTable.Columns.Add(language);

            var admins = userRep.GetAllAdmin();
            foreach (var admin in admins)
            {
                    DataRow AdminRow = UserTable.NewRow();
                    AdminRow[0] = admin.id;
                    AdminRow[1] = admin.firstname;
                    AdminRow[2] = admin.lastname;
                    AdminRow[3] = admin.email;
                    AdminRow[4] = admin.active;
                    AdminRow[5] = admin.phone_number;
                    AdminRow[6] = admin.language;
                    UserTable.Rows.Add(AdminRow);
                    AllAdmin.ItemsSource = UserTable.DefaultView;
            }

        }
        void filingAllLibrarianGridUsingDataTable()
        {
            DataTable UserTable = new DataTable();
            DataColumn id = new DataColumn("ID", typeof(int));
            DataColumn firstname = new DataColumn("Firstname", typeof(string));
            DataColumn lastname = new DataColumn("Lastname", typeof(string));
            DataColumn email = new DataColumn("Email", typeof(string));
            DataColumn active = new DataColumn("Active", typeof(Boolean));
            DataColumn phoneNumber = new DataColumn("Phonenumber", typeof(string));
            DataColumn language = new DataColumn("Language", typeof(string));

            UserTable.Columns.Add(id);
            UserTable.Columns.Add(firstname);
            UserTable.Columns.Add(lastname);
            UserTable.Columns.Add(email);
            UserTable.Columns.Add(active);
            UserTable.Columns.Add(phoneNumber);
            UserTable.Columns.Add(language);

            var librarians = userRep.GetAllLibrarian();
            foreach (var librarian in librarians)
            {
                    DataRow LibrarianRow = UserTable.NewRow();
                    LibrarianRow[0] = librarian.id;
                    LibrarianRow[1] = librarian.firstname;
                    LibrarianRow[2] = librarian.lastname;
                    LibrarianRow[3] = librarian.email;
                    LibrarianRow[4] = librarian.active;
                    LibrarianRow[5] = librarian.phone_number;
                    LibrarianRow[6] = librarian.language;
                    UserTable.Rows.Add(LibrarianRow);
                    AllLibrarian.ItemsSource = UserTable.DefaultView;
            }
        }

        void filingAllNormalUserGridUsingDataTable()
        {
            DataTable UserTable = new DataTable();
            DataColumn id = new DataColumn("ID", typeof(int));
            DataColumn firstname = new DataColumn("Firstname", typeof(string));
            DataColumn lastname = new DataColumn("Lastname", typeof(string));
            DataColumn email = new DataColumn("Email", typeof(string));
            DataColumn active = new DataColumn("Active", typeof(Boolean));
            DataColumn phoneNumber = new DataColumn("Phonenumber", typeof(string));
            DataColumn language = new DataColumn("Language", typeof(string));

            UserTable.Columns.Add(id);
            UserTable.Columns.Add(firstname);
            UserTable.Columns.Add(lastname);
            UserTable.Columns.Add(email);
            UserTable.Columns.Add(active);
            UserTable.Columns.Add(phoneNumber);
            UserTable.Columns.Add(language);

            var users = userRep.GetAllSimpleUser();
            foreach (var user in users)
            {
                    DataRow UserRow = UserTable.NewRow();
                    UserRow[0] = user.id;
                    UserRow[1] = user.firstname;
                    UserRow[2] = user.lastname;
                    UserRow[3] = user.email;
                    UserRow[4] = user.active;
                    UserRow[5] = user.phone_number;
                    UserRow[6] = user.language;
                    UserTable.Rows.Add(UserRow);
                    AllUsers.ItemsSource = UserTable.DefaultView;
            }
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

        private void DeactivateUser(object sender, RoutedEventArgs e)
        {
            userRep.deactivateUser(selectedUser.id);
            MessageBox.Show("Sikeres Deaktiválás");
            filingAllAdminGridUsingDataTable();
            filingAllLibrarianGridUsingDataTable();
            filingAllNormalUserGridUsingDataTable();
        }
        private void ActivateUser(object sender, RoutedEventArgs e)
        {
            userRep.activateUser(selectedUser.id);
            MessageBox.Show("Sikeres Aktiválás");
            filingAllAdminGridUsingDataTable();
            filingAllLibrarianGridUsingDataTable();
            filingAllNormalUserGridUsingDataTable();
        }

        private void AllAdmin_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {

            DataGrid gd = (DataGrid)sender;
            DataRowView row_selected = gd.SelectedItem as DataRowView;
            if (row_selected != null)
            {
                selectedUser.id = Convert.ToInt32(row_selected["id"].ToString());
 
            }

        }

        private void AllLibrarian_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            DataGrid gd = (DataGrid)sender;
            DataRowView row_selected = gd.SelectedItem as DataRowView;
            if (row_selected != null)
            {
                selectedUser.id = Convert.ToInt32(row_selected["id"].ToString());

            }
 
        }

        private void AllUsers_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            DataGrid gd = (DataGrid)sender;
            DataRowView row_selected = gd.SelectedItem as DataRowView;
            if (row_selected != null)
            {
                selectedUser.id = Convert.ToInt32(row_selected["id"].ToString());
                selectedUser.firstname = row_selected["firstname"].ToString();
                selectedUser.lastname = row_selected["lastname"].ToString();
                selectedUser.email = row_selected["email"].ToString();
                selectedUser.active = Convert.ToBoolean(row_selected["active"].ToString());
                selectedUser.phone_number = row_selected["phoneNumber"].ToString();
                selectedUser.language = row_selected["language"].ToString();

            }

        }

        
    }
}

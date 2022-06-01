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
    /// Interaction logic for AddUser.xaml
    /// </summary>
    public partial class AddUser : Window
    {
       private UserRepository userRepo = new UserRepository();
        public AddUser()
        {
            InitializeComponent();

            List<user_type> userTypes = userRepo.GetAllUserType();
            int userTypeCount = userTypes.Count();
            for (int i = 0; i < userTypeCount; i++)
            {
                RoleType.Items.Add(userTypes[i]);
            }

            if (MainWindow.roleType == 1)
            {
                RoleType.IsEnabled = true;
                RoleType.Visibility = Visibility.Visible;

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

        private void UserAddButtonClick(object sender, RoutedEventArgs e)
        {
            
            
            string first = firstName.Text.Substring(0, 2).ToLower();
            string last = lastName.Text.Substring(0, 3).ToLower();
            string userName = first + lastName.Text;
            string qr = first + last + postCode.Text;
            string pass = first + last + postCode.Text.ToString();
            int selectedRole = 3;
            if (MainWindow.roleType == 1)
            {
                RoleType.IsEnabled = true;
                selectedRole = Int32.Parse(RoleType.SelectedValue.ToString());

            }
            users user = new users()
            {
                firstname = firstName.Text,
                lastname = lastName.Text,
                email = userEmail.Text,
                username = userName,
                active = true,
                user_type_id = selectedRole,
                QR_code = qr,
                created_date = DateTime.Now,
                created_by = MainWindow.konyvtarosGlobalUsername,
                encrypted_password = pass,
                postcode = postCode.Text,
                city = city.Text,
                street = street.Text,
                street_number = streetNumber.Text,
               
                birth_date = userDateofBirth.SelectedDate,
                phone_number = phoneNumber.Text
            };
            

            userRepo.Insert(user);
            AddUser addUser  = new AddUser();
            addUser.Show();
            this.Close();


        }

 

        private void lastNameFocus(object sender, RoutedEventArgs e)
        {
            lastName.SelectAll();

        }

        private void emailFocus(object sender, RoutedEventArgs e)
        {
            userEmail.SelectAll();
        }

        private void postCodeFocus(object sender, RoutedEventArgs e)
        {
            postCode.SelectAll();
        }

        private void cityFocus(object sender, RoutedEventArgs e)
        {
            city.SelectAll();

        }

        private void streetFocus(object sender, RoutedEventArgs e)
        {
            street.SelectAll();
        }

        private void streeNumberFocus(object sender, RoutedEventArgs e)
        {
            streetNumber.SelectAll();
        }

        private void vezetekNevEvent(object sender, MouseButtonEventArgs e)
        {
            firstName.SelectAll();
        }

        private void phoneFocus(object sender, RoutedEventArgs e)
        {
            phoneNumber.SelectAll();
        }
    }
}

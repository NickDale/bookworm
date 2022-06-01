using BookWorm.Models;
using BookWorm.Repository;

using System;

using System.Windows;

using System.Windows.Input;


namespace BookWorm
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private UserRepository userRepo = new UserRepository();
        public static string konyvtarosGlobalUsername;
        public static int roleType;




        public MainWindow()
        {
            InitializeComponent();
            


        }

        private void LoginButton_Click(object sender, RoutedEventArgs e)
        {
            
            users oneUser = userRepo.LoginUser(txtUsername.Text);
            string hiba = "A Felhasználó név vagy jelszó helytelen";

            if (txtPassword.Password != "" && txtUsername.Text != "" || oneUser != null)
            {
                try
                {

                    //.NullReferenceException-el elszáll ha csak a belépésre nyomsz try catch nem segít 
                    if (oneUser.encrypted_password == txtPassword.Password && oneUser.username.ToLower() == txtUsername.Text.ToLower() && oneUser.active == true)
                    {
                        konyvtarosGlobalUsername = oneUser.username;
                        roleType = oneUser.user_type_id;
                        if (oneUser.user_type_id == 1)
                        {
                            AdminPage adminPage = new AdminPage();
                            adminPage.Show();

                        }
                        else if (oneUser.user_type_id == 2)
                        {
                            MenuWindow menu = new MenuWindow();
                            menu.Show();

                        }
                        else
                        {
                            MessageBox.Show("Nincs jogosultságod belépni");
                        }
                    }
                    else
                    {
                        MessageBox.Show(hiba);
                    }
                    this.Close();


                }
                // NullReferenceException-el lett próbálva ugyanúgy törik ..
                catch (Exception )
                {
                    MessageBox.Show("Nincs ilyen felhasználó");
                    
                }
            }


        }

        private void txt_Pass(object sender, RoutedEventArgs e)
        {
            txtPassword.SelectAll();
        }

        private void CloseApp(object sender, MouseButtonEventArgs e)
        {
            try
            {
                Close();
            }
           catch(Exception ex)
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

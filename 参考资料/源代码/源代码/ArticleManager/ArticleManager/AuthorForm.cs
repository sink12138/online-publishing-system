using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ArticleManager
{
    public partial class AuthorForm : Form
    {
        public AuthorForm()
        {
            InitializeComponent();
        }

        private void AddAuthor_button_Click(object sender, EventArgs e)
        {
            AddAuthor_button.Enabled = false;
            AddAuthorForm aaf = new AddAuthorForm();
            aaf.ShowDialog();
            AddAuthor_button.Enabled = true;
            this.Close();
        }

        private void UpdateAuthor_button_Click(object sender, EventArgs e)
        {
            UpdateAuthor_button.Enabled = false;
            UpdateAuthorForm uaf = new UpdateAuthorForm();
            uaf.ShowDialog();
            UpdateAuthor_button.Enabled = true;
            this.Close();
        }

        private void AuthorForm_Load(object sender, EventArgs e)
        {
            this.FormBorderStyle = FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
        }
    }
}

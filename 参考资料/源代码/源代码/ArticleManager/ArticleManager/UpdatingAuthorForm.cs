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
    public partial class UpdatingAuthorForm : Form
    {
        public UpdatingAuthorForm()
        {
            InitializeComponent();
        }
        public UpdatingAuthorForm(string id,string name,string email)
        {
            InitializeComponent();
            textBox3.Text = name;
            textBox4.Text = email;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (textBox3.Text.ToString().Length == 0 || textBox4.Text.ToString().Length==0)
            {
                MessageBox.Show("请将信息填写完整");
                return;
            }
            button1.Enabled = false;
            UpdateAuthorForm fa = (UpdateAuthorForm)this.Owner;
            fa.newname = textBox3.Text;
            fa.newemail = textBox4.Text;
            fa.operationSucceed = true;
            this.Close();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            UpdateAuthorForm fa = (UpdateAuthorForm)this.Owner;
            fa.operationSucceed = false;
            this.Close();
        }

        private void textBox4_TextChanged(object sender, EventArgs e)
        {

        }

        private void label4_Click(object sender, EventArgs e)
        {

        }

        private void textBox3_TextChanged(object sender, EventArgs e)
        {

        }

        private void label3_Click(object sender, EventArgs e)
        {

        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void UpdatingAuthorForm_Load(object sender, EventArgs e)
        {
            this.FormBorderStyle = FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
        }
    }
}

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
    public partial class UpdatingReviewerForm : Form
    {
        public UpdatingReviewerForm()
        {
            InitializeComponent();
        }
        public UpdatingReviewerForm(string id, string name, string email)
        {
            InitializeComponent();
            textBox1.Text = id;
            textBox3.Text = name;
            textBox4.Text = email;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (textBox3.Text.ToString().Length == 0 || textBox4.Text.ToString().Length == 0)
            {
                MessageBox.Show("请将信息填写完整");
                return;
            }
            button1.Enabled = false;
            UpdateReviewerForm fa = (UpdateReviewerForm)this.Owner;
            fa.newname = textBox3.Text;
            fa.newemail = textBox4.Text;
            fa.operationSucceed = true;
            this.Close();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            UpdateReviewerForm fa = (UpdateReviewerForm)this.Owner;
            fa.operationSucceed = false;
            this.Close();
        }

        private void UpdatingReviewerForm_Load(object sender, EventArgs e)
        {
            this.FormBorderStyle = FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
        }

        private void textBox3_TextChanged(object sender, EventArgs e)
        {

        }
    }
}

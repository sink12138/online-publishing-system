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
    public partial class MainPage : Form
    {
        public MainPage()
        {
            InitializeComponent();
        }

        private void 主页_Load(object sender, EventArgs e)
        {
            this.FormBorderStyle = FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Author_button.Enabled = false;
            Form Au = new AuthorForm();
            Au.ShowDialog();
            Author_button.Enabled = true;
        }

        private void button1_Click_1(object sender, EventArgs e)
        {
            Reviewer_button.Enabled = false;
            Form re = new ReviewerForm();
            re.ShowDialog();
            Reviewer_button.Enabled = true;
        }

        private void button1_Click_2(object sender, EventArgs e)
        {
            button1.Enabled = false;
            Form uaf = new UpdateArticleForm();
            uaf.ShowDialog();
            button1.Enabled = true;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            button2.Enabled = false;
            Form aaf = new AddArticleForm();
            aaf.ShowDialog();
            button2.Enabled = true;
        }

        private void button3_Click(object sender, EventArgs e)
        {
            button3.Enabled = false;
            CheckArticleState cas = new CheckArticleState();
            cas.ShowDialog();
            button3.Enabled = true;
        }
    }
}
